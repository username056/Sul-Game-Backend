package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
import org.sejong.sulgamewiki.service.ReportService;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("")
  public ResponseEntity<ReportDto> createReport(
      @RequestBody ReportCommand reportCommand) {
    ReportDto dto = reportService.createReport(reportCommand);
    return ResponseEntity.ok(dto);
  }
}
