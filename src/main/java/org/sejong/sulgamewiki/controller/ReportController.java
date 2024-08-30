package org.sejong.sulgamewiki.controller;

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
public class ReportController {
  private final ReportService reportService;

//  @PostMapping("")
//  public ResponseEntity<ReportDto> createReport(
//      @RequestBody ReportCommand reportCommand) {
//    ReportDto dto = reportService.createReport(reportCommand);
//    return ResponseEntity.ok(dto);
//  }
}
