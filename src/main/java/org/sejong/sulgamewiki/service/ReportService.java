package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Comment;
import org.sejong.sulgamewiki.object.Member;
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
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
  private final ReportRepository reportRepository;
  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final CommentRepository commentRepository;

  public ReportDto createReport(ReportCommand command) {
    ReportDto dto = ReportDto.builder().build();

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Object sourceObject = findSourceObject(command.getSourceId(), command.getSourceType());

    Report report = Report.builder()
        .reportedBy(member)
        .sourceType(command.getSourceType())
        .sourceId(command.getSourceId())
        .reportType(command.getReportType())
        .build();

    Report savedReport = reportRepository.save(report);
    dto.setReport(savedReport);

    return dto;
  }

  private Object findSourceObject(Long sourceId, SourceType sourceType) {
    switch (sourceType) {
      case INTRO:
      case OFFICIAL_GAME:
      case CREATION_GAME:
        return basePostRepository.findById(sourceId)
            .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
      case COMMENT:
        return commentRepository.findById(sourceId)
            .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
      default:
        throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }
  }

}