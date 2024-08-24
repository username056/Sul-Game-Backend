package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.service.MockService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/mock")
@RequiredArgsConstructor
@Tag(name = "테스트 데이터 관리 API", description = "목데이터 관리 API 제공")
public class MockController {
  private final MockService mockService;

  @PostMapping("/member")
  @LogMonitoringInvocation
  @Operation(summary = "모의 회원 생성", description = "모의 회원 생성")
  public ResponseEntity<MemberDto> createMockMember(){
    return ResponseEntity.ok(mockService.createMockMember());
  }

}
