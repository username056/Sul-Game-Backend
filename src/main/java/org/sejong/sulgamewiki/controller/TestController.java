package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.TestCommand;
import org.sejong.sulgamewiki.object.TestDto;
import org.sejong.sulgamewiki.service.TestService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @PostMapping(value = "/member/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  @Operation(
      summary = "[주의] 지정한 이메일의 회원 삭제",
      description = """
    **회원 삭제 API**

    주어진 이메일에 해당하는 회원을 시스템에서 완전히 삭제합니다. 이 작업은 회원의 모든 관련 데이터(예: 게시물, 상호작용 데이터)를 제거하며, 신중히 사용해야 합니다.

    **입력 파라미터 값:**

    - **`String email`**: 삭제할 회원의 이메일 주소 (예: "example@domain.com")
      _이메일은 반드시 존재하는 회원의 것이어야 합니다._

    **반환 파라미터 값:**

    - **`Long deletedMemberId`**: 삭제된 회원의 고유 ID
    - **`Long deletedMemberContentInteractionId`**: 삭제된 회원의 콘텐츠 상호작용 데이터의 고유 ID

    **예외 상황:**

    - **`CustomException`**: 지정된 이메일에 해당하는 회원이 존재하지 않을 경우 `MEMBER_NOT_FOUND` 오류를 발생시킵니다.

    **주의사항:**

    - 이 작업은 복구할 수 없으며, 회원의 모든 데이터를 완전히 삭제합니다. 해당 회원이 작성한 모든 게시물, 댓글, 좋아요 정보도 함께 삭제되므로 신중하게 사용해야 합니다.
    """
  )
  public ResponseEntity<TestDto> deleteMockMemberByEmail(
      @ModelAttribute TestCommand command) {
    return ResponseEntity.ok(testService.deleteMockMemberByEmail(command));
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
