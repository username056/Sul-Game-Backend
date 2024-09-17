package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.object.ExpLog;
import org.sejong.sulgamewiki.object.constants.ExpLevel;
import org.sejong.sulgamewiki.object.constants.ExpRule;
import org.sejong.sulgamewiki.object.constants.ReportType;
import org.sejong.sulgamewiki.repository.ExpLogRepository;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.sejong.sulgamewiki.util.annotation.LogMonitoringInvocation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpManagerService {
  private final MemberInteractionRepository memberInteractionRepository;
  private final ExpLogRepository expLogRepository;

  /**
   * 경험치를 변경합니다.
   * <p>
   * 주어진 규칙에 따라 사용자의 경험치를 증가, 감소 시키고, 경험치 변동 내역을 기록합니다.
   *
   * @param member 경험치를 받을 사용자
   * @param expRule 경험치 규칙
   */
  @Transactional
  @LogMonitoringInvocation
  public void updateExp(Member member, ExpRule expRule) {
    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    // 경험치 업데이트
    interaction.addExp(expRule.getExp());

    // 경험치에 따른 레벨 업데이트
    interaction.setExpLevel(ExpLevel.calculateLevel(interaction.getExp()));

    // Interaction 저장
    memberInteractionRepository.save(interaction);

    // 경험치 변동 기록 생성
    expLogRepository.save(
        ExpLog.builder()
            .member(member)
            .expChanged(expRule.getExp())
            .description(expRule.getDescription())
            .build());

    log.info("[ 경험치 EXP ] 사용자 {}에게 {}점 부여 (사유: {})", member.getEmail(), expRule.getExp(), expRule.getDescription());
  }

  /**
   * 관리자가 신고된 사용자에 대해 제재를 가합니다.
   * <p>
   * 신고 사유에 따라 경험치를 자동으로 차감하며, 관리자는 따로 점수를 입력하지 않고 신고 타입을 선택하면 됩니다.
   *
   * @param member 제재 대상 사용자
   * @param reportType 신고 사유
   */
  @LogMonitoringInvocation
  public void applyPenaltyForReport(Member member, ReportType reportType) {
    ExpRule expRule = null;

    // 비속어 및 모욕 신고에 따른 제재
    if (reportType == ReportType.PROFANITY || reportType == ReportType.LEAKAGE_IMPERSONATION) {
      expRule = ExpRule.PENALTY_HARASSMENT;

      // 정치적 발언, 스팸 또는 도배, 상업적 광고에 대한 제재
    } else if (reportType == ReportType.POLITICAL || reportType == ReportType.SPAM || reportType == ReportType.COMMERCIAL_AD) {
      expRule = ExpRule.PENALTY_SPAM_POST;

      // 음란물 신고에 따른 제재
    } else if (reportType == ReportType.OBSCENE_CONTENT) {
      expRule = ExpRule.PENALTY_OBSCENE_CONTENT;

      // 기타 또는 부적절한 내용일 경우 (관리자가 수동 조정)
    } else if (reportType == ReportType.INAPPROPRIATE_CONTENT || reportType == ReportType.OTHER) {
      expRule = ExpRule.ADMIN_EXP_ADJUSTMENT;

      // 제재 규칙이 정의되지 않은 경우 (기본 처리)
    } else {
      log.info("정의되지않은 report type: " + reportType);
    }

    // 신고 사유에 따른 경험치 차감 처리
    if (expRule != null) {
      updateExp(member, expRule);
      log.info("사용자 {}에게 {}에 대한 제재가 적용됨 (사유: {})", member.getEmail(), expRule.getExp(), reportType.getDescription());
    } else {
      log.error("사용자의 제제내용을 찾을 수 없습니다 : ExpRule is null");
    }
  }
}
