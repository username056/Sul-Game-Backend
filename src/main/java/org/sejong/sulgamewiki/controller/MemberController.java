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
@Tag(name = "회원 관리 API", description = "회원 관리 API 제공")
public class MemberController {

  private final MemberService memberService;
  private final CommentService commentService;

  @PostMapping("/complete-registration")
  @LogMonitoringInvocation
  @Operation(
      summary = "회원 등록 완료",
      description = "소셜로그인을 이후에 나머지 회원가입 완료\n\n"
          + "Input:\n\n"
          + "String nickname\n\n"
          + "LocalDate birthDate\n\n"
          + "String college\n\n"
          + "Boolean isUniversityPublic\n\n"
          + "Boolean isNotificationEnabled")
  public ResponseEntity<MemberDto> completeRegistration(
      @ModelAttribute MemberCommand command
  ) {
    return ResponseEntity.ok(memberService.completeRegistration(command));
  }

  @GetMapping("/profile")
  @LogMonitoringInvocation
  @Operation(
      summary = "마이페이지",
      description = "회원 마이페이지 정보 제공\n\n"
          + "Input:\n\n"
          + "Long memberId")
  public ResponseEntity<MemberDto> getProfile(
      @ModelAttribute MemberCommand command
  ) {
    return ResponseEntity.ok(memberService.getProfile(command));
  }

  @GetMapping("/liked-posts")
  @LogMonitoringInvocation
  @Operation(
      summary = "좋아요한 글",
      description = "회원이 좋아요 한 글 제공\n\n"
          + "Input:\n\n"
          + "Long memberId")
  public ResponseEntity<MemberDto> getLikedPosts(
      @ModelAttribute MemberCommand command
  ) {
    return ResponseEntity.ok(memberService.getLikedPosts(command));
  }

  @GetMapping("/bookmarked-posts")
  @LogMonitoringInvocation
  @Operation(
      summary = "즐겨찾기한 글",
      description = "회원이 즐겨찾기 한 글 제공\n\n"
          + "Input:\n\n"
          + "Long memberId")
  public ResponseEntity<MemberDto> getBookmarkedPosts(
      @ModelAttribute MemberCommand command
  ) {
    return ResponseEntity.ok(memberService.getBookmarkedPosts(command));
  }

  //TODO: member 프로필사진 편집
  //TODO: member 정보 편집
}
