package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.service.CommentService;
import org.sejong.sulgamewiki.service.MemberService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "회원 관리 API",
    description = "회원 관리 API 제공"
)
public class MemberController {

  private final MemberService memberService;
  private final CommentService commentService;

  @PostMapping("/complete-registration")
  @LogMonitoringInvocation
  @Operation(
      summary = "회원 등록 완료",
      description = """
    **회원 등록 완료**

    소셜로그인 이후 나머지 회원가입을 완료합니다.

    **입력 파라미터 값:**

    - **`String nickname`**: 회원의 닉네임 (예: "서새찬")
      _최대 20자, 특수문자 사용 불가_

    - **`LocalDate birthDate`**: 회원의 생년월일 (예: "1999-10-29")  
      _형식: YYYY-MM-DD_

    - **`String college`**: 회원의 대학 이름 (예: "세종대학교")  
      _최대 50자_

    - **`Boolean isUniversityPublic`**: 대학 공개 여부 (예: "true" or "false")  
      `기본값: true`

    - **`Boolean isNotificationEnabled`**: 알림 수신 여부 (예: "true" or "false")  
      `기본값: true`

    **반환 파라미터 값:**

    - **`Member member`**: 회원가입이 완료된 회원
    """
  )
  public ResponseEntity<MemberDto> completeRegistration(
      @ModelAttribute MemberCommand command) {
    return ResponseEntity.ok(memberService.completeRegistration(command));
  }

  @GetMapping("/profile")
  @LogMonitoringInvocation
  @Operation(
      summary = "마이페이지",
      description = "회원 마이페이지 정보 제공\n\n"
          + "[입력 파라미터 값] : \n\n"
          + "Long memberId\n\n"
          + "[반환 파라미터 값] : \n\n"
          + "Member member\n\n"
          + "MemberContentInteraction memberContentInteraction"
  )
  public ResponseEntity<MemberDto> getProfile(
      @ModelAttribute MemberCommand command) {
    return ResponseEntity.ok(memberService.getProfile(command));
  }

  @GetMapping("/liked-posts")
  @LogMonitoringInvocation
  @Operation(
      summary = "좋아요한 글",
      description = "회원이 좋아요 한 글 제공\n\n"
          + "[입력 파라미터 값] : \n\n"
          + "Long memberId\n\n"
          + "[반환 파라미터 값] : \n\n"
          + "List<BasePost> likedOfficialGames\n\n"
          + "List<BasePost> likedCreationGame\n\n"
          + "List<BasePost> likedIntros\n\n"
  )
  public ResponseEntity<MemberDto> getLikedPosts(
      @ModelAttribute MemberCommand command) {
    return ResponseEntity.ok(memberService.getLikedPosts(command));
  }

  @GetMapping("/bookmarked-posts")
  @LogMonitoringInvocation
  @Operation(
      summary = "즐겨찾기한 글",
      description = "회원이 즐겨찾기 한 글 제공\n\n"
          + "[입력 파라미터 값] : \n\n"
          + "Long memberId\n\n"
          + "[반환 파라미터 값] : \n\n"
          + "List<BasePost> bookmarkedOfficialGameIds\n\n"
          + "List<BasePost> bookmarkedCreationGameIds\n\n"
          + "List<BasePost> bookmarkedIntroIds\n\n"
  )
  public ResponseEntity<MemberDto> getBookmarkedPosts(
      @ModelAttribute MemberCommand command) {
    return ResponseEntity.ok(memberService.getBookmarkedPosts(command));
  }

  //TODO: member 프로필사진 편집
  //TODO: member 정보 편집
}
