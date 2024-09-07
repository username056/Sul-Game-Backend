package org.sejong.sulgamewiki.service;

import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberRankingService {

  private final MemberInteractionRepository memberInteractionRepository;
  private final MemberRepository memberRepository;

  @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
  @Async
  @Transactional
  public void updatePreviousRanks() {
    log.info("순위 업데이트 작업을 시작합니다.");
    List<MemberInteraction> interactions = memberInteractionRepository.findAll();
    interactions.sort(Comparator.comparingLong(MemberInteraction::getExp).reversed());

    int rank = 1;
    for (MemberInteraction interaction : interactions) {
      int previousRank = interaction.getCurrentRank();
      interaction.setPreviousRank(previousRank);
      interaction.setCurrentRank(rank);
      log.info("회원 ID: {}, 현재 순위: {}, 이전 순위: {}, 순위 차이: {}", interaction.getMember().getMemberId(), rank, previousRank,
          previousRank - rank);
      rank++;
    }
    memberInteractionRepository.saveAll(interactions);
    log.info("순위 업데이트 작업을 완료했습니다.");
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
    double progressPercentToNextLevel = ((double) memberInteraction.getExp() / nextLevelExp) * 100;

    command.setExpRank(expRank);
    command.setExpRankPercentile(expRankPercentile);
    command.setNextLevelExp(nextLevelExp);
    command.setRemainingExpForNextLevel(remainingExpForNextLevel);
    command.setProgressPercentToNextLevel(progressPercentToNextLevel);
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
        .build();
  }
}