package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Comment;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.Report;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
import org.sejong.sulgamewiki.object.constants.ExpRule;
import org.sejong.sulgamewiki.object.constants.ReportStatus;
import org.sejong.sulgamewiki.object.constants.ScoreRule;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.CommentRepository;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.repository.ReportRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final ReportRepository reportRepository;
  private final CommentRepository commentRepository;
  private final MemberInteractionRepository memberInteractionRepository;
  private final ExpManagerService expManagerService;

  public ReportDto createReport(ReportCommand command) {

    // 멤버 조회
    Member reporter = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 리포트 생성 및 저장
    Report report = Report.builder()
        .reporter(reporter)
        .sourceType(command.getSourceType())
        .sourceId(command.getSourceId())
        .reportType(command.getReportType())
        .build();

    Report savedReport = reportRepository.save(report);

    return ReportDto.builder()
        .report(savedReport)
        .build();
  }

  public boolean isAlreadyReported(ReportCommand command) {
    Member reporter = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    return reportRepository.existsByReporterAndSourceIdAndSourceType(reporter,
        command.getSourceId(), command.getSourceType());
  }

  // 게시물 신고 로직
  public ReportDto reportBasePost(ReportCommand command) {

    // 신고할 게시물
    BasePost basePost = basePostRepository.findById(command.getSourceId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 게시글 작성자
    Member postOwner = memberRepository.findById(
            basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 신고자
    Member reporter = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 중복 신고 여부 확인
    if (isAlreadyReported(command)) {
      throw new CustomException(ErrorCode.MEMBER_ALREADY_REPORTED);
    }

    // 신고자 EXP 갱신
    expManagerService.updateExp(reporter, ExpRule.REPORT_FILED);

    /*
    TODO: 신고가 유효한 신고인지 관리자가 검증하는 로직 필요
     */

    // 게시글 신고횟수 증가
    basePost.increaseReportedCount();
    // 게시글 SCORE 갱신
    basePost.updateScore(ScoreRule.REPORTED);

    // 신고자 EXP 갱신
    expManagerService.updateExp(reporter, ExpRule.REPORT_ACCEPTED);
    // 신고당한 멤버 EXP 갱신
    expManagerService.applyPenaltyForReport(postOwner, command.getReportType());

    return createReport(command);
  }

  // 댓글 신고 로직
  public ReportDto reportComment(ReportCommand command) {

    // 신고할 댓글
    Comment comment = commentRepository.findById(command.getSourceId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    // 게시글 작성자
    Member postOwner = memberRepository.findById(
            comment.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 신고자
    Member reporter = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 중복 신고 여부 확인
    if (isAlreadyReported(command)) {
      throw new CustomException(ErrorCode.MEMBER_ALREADY_REPORTED);
    }

    // 신고자 EXP 갱신
    expManagerService.updateExp(reporter, ExpRule.REPORT_FILED);

    /*
    TODO: 신고가 유효한 신고인지 관리자가 검증하는 로직 필요
     */

    // 댓글 신고횟수 증가
    comment.increaseReportedCount();

    // 신고자 EXP 갱신
    expManagerService.updateExp(reporter, ExpRule.REPORT_ACCEPTED);
    // 신고당한 멤버 EXP 갱신
    expManagerService.applyPenaltyForReport(postOwner, command.getReportType());

    return createReport(command);
  }

  // TODO: 신고 접수 후 관리자 검토 상태로 변경하는 로직 필요
  public ReportDto submitReportForReview(Report report) {
    report.setStatus(ReportStatus.PENDING);
    Report savedReport = reportRepository.save(report);
    notifyAdmin(savedReport); // 관리자에게 알림 전송
    return ReportDto.builder()
        .report(savedReport)
        .build();
  }

  // 관리자에게 알림을 보내는 메서드
  private void notifyAdmin(Report report) {
    // 메일, 슬랙 메시지, UI 알림 등으로 관리자에게 알림 전송
  }

}