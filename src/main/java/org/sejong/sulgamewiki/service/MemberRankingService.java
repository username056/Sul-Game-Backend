package org.sejong.sulgamewiki.service;

import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberRankingService {
  private final MemberInteractionRepository interactionRepository;

  @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
  @Async
  @Transactional
  public void updatePreviousRanks() {
    log.info("순위 업데이트 작업을 시작합니다.");
    List<MemberInteraction> interactions = interactionRepository.findAll();
    interactions.sort(Comparator.comparingLong(MemberInteraction::getExp).reversed());

    int rank = 1;
    for (MemberInteraction interaction : interactions) {
      int previousRank = interaction.getCurrentRank();
      interaction.setPreviousRank(previousRank);
      interaction.setCurrentRank(rank);
      log.info("회원 ID: {}, 현재 순위: {}, 이전 순위: {}, 순위 차이: {}", interaction.getMember().getMemberId(), rank, previousRank, previousRank - rank);
      rank++;
    }
    interactionRepository.saveAll(interactions);
    log.info("순위 업데이트 작업을 완료했습니다.");
  }

  @LogMonitoringInvocation
  public void calculateRankAndPercentile(MemberCommand command) {
    MemberInteraction memberInteraction = command.getMemberInteraction();
    long totalMembers = interactionRepository.countAllMembers();
    long higherExpMemberCount = interactionRepository.countMembersWithMoreExpThan(memberInteraction.getExp());

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
}