package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.MockDto;
import org.sejong.sulgamewiki.service.MockService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/mock")
@RequiredArgsConstructor
@Tag(
    name = "테스트 데이터 관리 API",
    description = "목데이터 관리 API 제공"
)
public class MockController {

  private final MockService mockService;

  @PostMapping("/member")
  @LogMonitoringInvocation
  @Operation(
      summary = "모의 회원 생성",
      description = "모의 회원 생성\n\n"
          + "임의의 이메일, 닉네임 을 가진 회원이 생성 됩니다."
  )
  public ResponseEntity<MockDto> createMockMember() {
    return ResponseEntity.ok(mockService.createMockMember());
  }

  @GetMapping("/member-list")
  @LogMonitoringInvocation
  @Operation(
      summary = "전체 회원 조희",
      description = "전체 회원 조회\n\n"
          + "DB에 저장되어있는 모든 회원의 ID값, 이메일, 닉네임을 확인하실 수 있습니다"
  )
  public ResponseEntity<MockDto> getAllMember() {
    return ResponseEntity.ok(mockService.getAllMember());
  }

  @DeleteMapping("/member")
  @LogMonitoringInvocation
  @Operation(
      summary = "[주의] 모의 회원 전체삭제",
      description = "[주의] 모의 회원 전체 삭제\n\n"
          + "DB에서 전체 회원과 회원관련 내용을 삭제합니다\n\n"
          + "사용에 주의해주세요!!")
  public ResponseEntity<MockDto> deleteAllMockMember() {
    return ResponseEntity.ok(mockService.deleteAllMockMember());
  }


}
