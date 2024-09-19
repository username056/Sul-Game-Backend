package org.sejong.sulgamewiki.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.DailyMemberRanking;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.object.constants.ExpLevel;
import org.sejong.sulgamewiki.object.constants.RankChangeStatus;
import org.sejong.sulgamewiki.repository.DailyMemberRankingRepository;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.repository.ExpLogRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.sejong.sulgamewiki.util.annotation.LogMonitoringInvocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberRankingService {

  private final MemberInteractionRepository memberInteractionRepository;
  private final MemberRepository memberRepository;
  private final DailyMemberRankingRepository dailyMemberRankingRepository;
  private final ExpLogRepository expLogRepository;

  // 매일 자정에 실행
  @Scheduled(cron = "0 0 0 * * ?")
  @Async
  @Transactional
  public void updatePreviousRanks() {
    log.info("매일 자정 순위 업데이트 작업을 시작합니다.");
    List<MemberInteraction> interactions = memberInteractionRepository.findAll();
    interactions.sort(Comparator.comparingLong(MemberInteraction::getExp).reversed());

    int rank = 1;
    for (MemberInteraction interaction : interactions) {
      int previousRank = interaction.getCurrentRank();
      interaction.setPreviousRank(previousRank);
      interaction.setCurrentRank(rank);
      log.info("회원 ID: {}, 현재 순위: {}, 이전 순위: {}, 순위 변화: {}",
          interaction.getMember().getMemberId(), rank, previousRank, previousRank - rank);
      rank++;
    }
    memberInteractionRepository.saveAll(interactions);
    log.info("매일 자정 순위 업데이트 작업을 완료했습니다.");
  }

  @LogMonitoringInvocation
  public void calculateRankAndPercentile(MemberCommand command) {
    MemberInteraction memberInteraction = command.getMemberInteraction();
    long totalMembers = memberInteractionRepository.countAllMembers();
    long higherExpMemberCount = memberInteractionRepository.countMembersWithMoreExpThan(memberInteraction.getExp());

    int expRank = (int) (higherExpMemberCount + 1);
    double expRankPercentile = ((double) expRank / totalMembers) * 100;

    long nextLevelExp = memberInteraction.getExpLevel().getNextLevelExp();
    long remainingExpForNextLevel = nextLevelExp - memberInteraction.getExp();
    double progressPercentToNextLevel;

    // 만약 A 레벨이라면 진행률을 100%로 설정
    if (memberInteraction.getExpLevel() == ExpLevel.A) {
      progressPercentToNextLevel = 100.0;
    } else {
      // A 레벨이 아닐 경우에는 정상적인 진행률 계산
      progressPercentToNextLevel = ((double) memberInteraction.getExp() / memberInteraction.getExpLevel().getNextLevelExp()) * 100;
    }

    int rankChange = memberInteraction.getPreviousRank() - memberInteraction.getCurrentRank();

    command.setExpRank(expRank);
    command.setExpRankPercentile(expRankPercentile);
    command.setNextLevelExp(nextLevelExp);
    command.setRemainingExpForNextLevel(remainingExpForNextLevel);
    command.setProgressPercentToNextLevel(progressPercentToNextLevel);
    command.setRankChange(rankChange);
  }

  public MemberDto reloadRankInfo(MemberCommand command) {

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    MemberInteraction memberInteraction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    command.setMemberInteraction(memberInteraction);
    calculateRankAndPercentile(command);

    log.info("회원 ID: {}, 새로운 순위: {}, 상위 퍼센트: {}",
        command.getMemberId(), command.getExpRank(), command.getExpRankPercentile());

    return MemberDto.builder()
        .exp(memberInteraction.getExp())
        .expRank(command.getExpRank())
        .expRankPercentile(command.getExpRankPercentile())
        .nextLevelExp(command.getNextLevelExp())
        .remainingExpForNextLevel(command.getRemainingExpForNextLevel())
        .progressPercentToNextLevel(command.getProgressPercentToNextLevel())
        .rankChange(command.getRankChange())
        .build();
  }

  /**
   * 매 3시간마다 상위 50명의 회원 랭킹을 업데이트하여 저장하는 스케줄러.
   */
  @Scheduled(cron = "0 0 0/3 * * ?") // 매 3시간마다
  @Async
  @Transactional
  public void saveTop50DailyMemberRankings() {
    log.info("상위 50명 회원 랭킹 저장 작업을 시작합니다.");

    // 경험치 내림차순으로 상위 50명의 회원을 조회
    List<MemberInteraction> topMembers = memberInteractionRepository.findTop50ByOrderByExpDesc();

    if (topMembers.isEmpty()) {
      log.info("[랭킹 50명 업데이트] : 회원을 찾을 수 없음");
      return;
    }

    LocalDateTime now = LocalDateTime.now();
    LocalDate today = now.toLocalDate();

    List<DailyMemberRanking> rankingsToSave = new ArrayList<>();
    int currentRank = 1;

    for (MemberInteraction interaction : topMembers) {
      Member member = interaction.getMember();
      Long currentExp = interaction.getExp();

      // 이 회원의 최신 랭킹 기록을 조회
      Optional<DailyMemberRanking> latestRankingOpt = dailyMemberRankingRepository.findTopByMemberOrderByUpdateTimeDesc(member);

      int previousRank = latestRankingOpt.map(DailyMemberRanking::getRank).orElse(0);
      int rankChange = (previousRank != 0) ? (previousRank - currentRank) : 0;

      // 랭크 변화 상태를 결정
      RankChangeStatus status;
      if (previousRank == 0) {
        status = RankChangeStatus.NEW;
      } else if (rankChange > 0) {
        status = RankChangeStatus.UP;
      } else if (rankChange < 0) {
        status = RankChangeStatus.DOWN;
      } else {
        status = RankChangeStatus.SAME;
      }

      // DailyMemberRanking 엔티티 생성
      DailyMemberRanking ranking = DailyMemberRanking.builder()
          .member(member)
          .recordDate(today)
          .rank(currentRank)
          .exp(currentExp)
          .rankChangeStatus(status)
          .rankChange(rankChange)
          .updateTime(now)
          .build();

      rankingsToSave.add(ranking);
      currentRank++;
    }

    // 모든 랭킹을 한 번에 저장
    dailyMemberRankingRepository.saveAll(rankingsToSave);
    log.info("총 {}개의 일일 회원 랭킹을 성공적으로 저장했습니다.", rankingsToSave.size());
  }

  /**
   * 최신 상위 50명의 일일 회원 EXP 랭킹을 페이징 처리하여 조회합니다.
   */
  @LogMonitoringInvocation
  public MemberDto getDailyMemberExpRankings(MemberCommand command) {

    // 랭크를 기준으로 오름차순 정렬된 Pageable 객체 생성
    Pageable pageable = PageRequest.of(
        command.getPageNumber(),
        command.getPageSize(),
        Sort.by("rank").ascending()
    );

    // 가장 최근의 업데이트 시간을 조회
    Optional<LocalDateTime> latestUpdateTimeOpt = dailyMemberRankingRepository.findMaxUpdateTime();

    if (latestUpdateTimeOpt.isEmpty()) {
      log.warn("랭킹 데이터가 존재하지 않습니다.");
      return MemberDto.builder()
          .rankingHistoryPage(Page.empty())
          .build();
    }

    LocalDateTime latestUpdateTime = latestUpdateTimeOpt.get();

    // 가장 최근 업데이트 시간에 해당하는 랭킹을 페이징 처리하여 조회
    Page<DailyMemberRanking> rankingsPage = dailyMemberRankingRepository.findByUpdateTime(latestUpdateTime, pageable);

    // MemberDto 객체에 랭킹 정보를 설정하여 반환
    MemberDto memberDto = MemberDto.builder()
        .rankingHistoryPage(rankingsPage)
        .pageNumber(pageable.getPageNumber())
        .pageSize(pageable.getPageSize())
        .build();

    return memberDto;
  }
}
