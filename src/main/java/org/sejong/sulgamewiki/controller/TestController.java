package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.TestCommand;
import org.sejong.sulgamewiki.object.TestDto;
import org.sejong.sulgamewiki.service.TestService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Tag(
    name = "테스트 데이터 관리 API",
    description = "테스트용 프로젝트 관리 API를 제공"
)
public class TestController {

  private final TestService testService;

  @PostMapping("/member")
  @LogMonitoringInvocation
  @Operation(
      summary = "모의 회원 생성",
      description = """
      **모의 회원 생성**

      임의의 이메일과 닉네임을 가진 회원을 생성합니다.
      """
  )
  public ResponseEntity<TestDto> createMockMember() {
    return ResponseEntity.ok(testService.createMockMember());
  }

  @GetMapping("/member-list")
  @LogMonitoringInvocation
  @Operation(
      summary = "전체 회원 조희",
      description = "전체 회원 조회\n\n"
          + "DB에 저장되어있는 모든 회원의 ID값, 이메일, 닉네임을 확인하실 수 있습니다"
  )
  public ResponseEntity<TestDto> getAllMember() {
    return ResponseEntity.ok(testService.getAllMember());
  }

  @DeleteMapping("/member")
  @LogMonitoringInvocation
  @Operation(
      summary = "[주의] 모의 회원 전체삭제",
      description = "[주의] 모의 회원 전체 삭제\n\n"
          + "DB에서 전체 회원과 회원관련 내용을 삭제합니다\n\n"
          + "사용에 주의해주세요!!")
  public ResponseEntity<TestDto> deleteAllMockMember() {
    return ResponseEntity.ok(testService.deleteAllMockMember());
  }

  @PostMapping("/post")
  @LogMonitoringInvocation
  @Operation(
      summary = "모의 포스트 생성",
      description = """
      **모의 포스트 생성**

      주어진 개수만큼 랜덤한 회원 ID를 사용하여 Intro, CreationGame, OfficialGame 포스트를 각각 생성합니다.
      """
  )
  public ResponseEntity<TestDto> createMockPost(
      @RequestBody TestCommand command) {
    return ResponseEntity.ok(testService.createMockPosts(command));
  }
}
