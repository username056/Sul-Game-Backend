package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Comment;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.Report;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
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

  public ReportDto createReport(ReportCommand command) {
    ReportDto dto = ReportDto.builder().build();

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    //TODO : 로직 sucks
    if(command.getSourceType().name().equals("INTRO") || command.getSourceType().name().equals("CREATION_GAME")){
      BasePost basePost = basePostRepository.findById(command.getSourceId())
          .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

      Report report = Report.builder()
          .reporter(member)
          .sourceType(command.getSourceType())
          .sourceId(command.getSourceId())
          .reportType(command.getReportType())
          .build();

      Report savedReport = reportRepository.save(report);
      dto.setReport(savedReport);

    } else if(command.getSourceType().name().equals("COMMENT")){
      Comment comment = commentRepository.findById(command.getSourceId())
          .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

      Report report = Report.builder()
          .reporter(member)
          .sourceType(command.getSourceType())
          .sourceId(command.getSourceId())
          .reportType(command.getReportType())
          .build();

      Report savedReport = reportRepository.save(report);
      dto.setReport(savedReport);
    }

    return dto;
  }
}
