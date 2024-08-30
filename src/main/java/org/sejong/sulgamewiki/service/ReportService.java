package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Comment;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.Report;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.CommentRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.repository.ReportRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final ReportRepository reportRepository;
  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final CommentRepository commentRepository;

//  public ReportDto createReport(ReportCommand command) {
//
//    // 멤버 확인
//    Member member = memberRepository.findById(command.getMemberId())
//        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//
//    // 신고 대상 객체 찾기
//    Object sourceObject = findSourceObject(command.getSourceId(),
//        command.getSourceType());
//
//    // 리포트 생성 및 저장
//    Report report = Report.builder()
//        .reporter(member)
//        .sourceType(command.getSourceType())
//        .sourceId(command.getSourceId())
//        .reportType(command.getReportType())
//        .build();
//    Report savedReport = reportRepository.save(report);
//
//    return ReportDto.builder()
//        .report(savedReport)
//        .build();
//  }
  private ReportDto createReport(ReportCommand command, Member member) {
    // 리포트 생성 및 저장
    Report report = Report.builder()
        .reporter(member)
        .sourceType(command.getSourceType())
        .sourceId(command.getSourceId())
        .reportType(command.getReportType())
        .build();
    Report savedReport = reportRepository.save(report);

    return ReportDto.builder()
        .report(savedReport)
        .build();
  }



  private Object findSourceObject(Long sourceId, SourceType sourceType) {
    if (sourceType == SourceType.INTRO || sourceType == SourceType.OFFICIAL_GAME || sourceType == SourceType.CREATION_GAME) {
      return basePostRepository.findById(sourceId)
          .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    } else if (sourceType == SourceType.COMMENT) {
      return commentRepository.findById(sourceId)
          .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    } else {
      throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }
  }

  public ReportDto getReport(Long reportId) {
    Report report = reportRepository.findById(reportId)
        .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));

    return ReportDto.builder()
        .report(report)
        .build();
  }

  public void increaseReportCount(Object sourceObject) {
    if (sourceObject instanceof BasePost) {
      BasePost basePost = (BasePost) sourceObject;
      basePost.setReportedCount(basePost.getReportedCount() + 1);
      basePostRepository.save(basePost);
    } else if (sourceObject instanceof Comment) {
      Comment comment = (Comment) sourceObject;
      comment.setReportedCount(comment.getReportedCount() + 1);
      commentRepository.save(comment);
    } else {
      throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }
  }

  public boolean isAlreadyReported(Member member, Long sourceId,
      SourceType sourceType) {
    return reportRepository.existsByReporterAndSourceIdAndSourceType(member,
        sourceId, sourceType);
  }

  // 게시물 신고 로직
  public ReportDto reportBasePost(ReportCommand reportCommand) {
    BasePost basePost = basePostRepository.findById(reportCommand.getSourceId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    Member member = memberRepository.findById(reportCommand.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 중복 신고 여부 확인
    if (isAlreadyReported(member, reportCommand.getSourceId(),
        reportCommand.getSourceType())) {
      throw new CustomException(ErrorCode.MEMBER_ALREADY_REPORTED);
    }

    // 리포트 생성
    ReportCommand command = ReportCommand.builder()
        .memberId(member.getMemberId())
        .sourceId(basePost.getBasePostId())
        .sourceType(reportCommand.getSourceType())
        .reportType(reportCommand.getReportType())
        .build();

    return createReport(command, member);
  }

  // 댓글 신고 로직
  public ReportDto reportComment(ReportCommand reportCommand){
      Comment comment = commentRepository.findById(reportCommand.getSourceId())
          .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

      Member member = memberRepository.findById(reportCommand.getMemberId())
          .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

      // 중복 신고 여부 확인
      if (isAlreadyReported(member, reportCommand.getSourceId(),
          reportCommand.getSourceType())) {
        throw new CustomException(ErrorCode.MEMBER_ALREADY_REPORTED);
      }

      ReportCommand command = ReportCommand.builder()
          .memberId(member.getMemberId())
          .sourceId(comment.getCommentId())
          .sourceType(reportCommand.getSourceType())
          .reportType(reportCommand.getReportType())
          .build();

      return createReport(command, member);

    }


}