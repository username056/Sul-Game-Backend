package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;

@Slf4j
@RequiredArgsConstructor
public abstract class BasePostService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final ReportService reportService;

  public ReportDto reportGame(ReportCommand reportCommand) {
    BasePost basePost = basePostRepository.findById(reportCommand.getSourceId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    Member member = memberRepository.findById(reportCommand.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 중복 신고 여부 확인
    boolean isAlreadyReported = reportService.isAlreadyReported(
        member, reportCommand.getSourceId(), reportCommand.getSourceType());
    if (isAlreadyReported) {
      throw new CustomException(ErrorCode.ALREADY_REPORTED);
    }

    ReportCommand command = createReportCommand(reportCommand, basePost, member);
    return reportService.createReport(command);
  }

  protected abstract ReportCommand createReportCommand(
      ReportCommand reportCommand, BasePost basePost, Member member);
}
