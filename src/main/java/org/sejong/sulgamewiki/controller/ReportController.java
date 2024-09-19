package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
import org.sejong.sulgamewiki.service.ReportService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
@Tag(
    name = "신고 관리 API",
    description = "신고 관리 API 제공"
)
public class ReportController {
  private final ReportService reportService;

  @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ReportDto> postReport(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute ReportCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    return ResponseEntity.ok(reportService.reportBasePost(command));
  }

  @PostMapping(value = "/comment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ReportDto> commentReport(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute ReportCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    return ResponseEntity.ok(reportService.reportComment(command));
  }
}
