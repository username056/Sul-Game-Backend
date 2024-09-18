package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.service.MemberRankingService;
import org.sejong.sulgamewiki.service.MemberService;
import org.sejong.sulgamewiki.object.CustomUserDetails;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class MemberController implements MemberControllerDocs{

  private final MemberService memberService;
  private final MemberRankingService memberRankingService;

  @PostMapping(value = "/complete-registration" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> completeRegistration(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.completeRegistration(command));
  }

  @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> getProfile(
      @AuthenticationPrincipal CustomUserDetails customUserDetails, //String memberId
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.getProfile(command));
  }

  @PostMapping(value = "/my-posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> getMyPosts(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command){
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.getMyPosts(command));
  }

  @PostMapping(value = "/liked-posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> getLikedPosts(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.getLikedPosts(command));
  }


  @PostMapping(value = "/bookmarked-posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> getBookmarkedPosts(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.getBookmarkedPosts(command));
  }

  @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> updateMemberProfileImage(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.updateMemberProfileImage(command));
  }

  @PostMapping(value = "/nickname", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> changeNickname(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.changeNickname(command));
  }

  @PostMapping(value = "/notification", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> changeNotificationSetting(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.changeNotificationSetting(command));
  }

  @PostMapping(value = "/check-nickname", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> checkDuplicateNickname(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.isDuplicationNickname(command));
  }

  @PostMapping(value = "/rank", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> reloadRankInfo(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberRankingService.reloadRankInfo(command));
  }

  @PostMapping(value = "/exp-logs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  public ResponseEntity<MemberDto> getExpLogs(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command) {
    command.setMemberId(Long.parseLong(customUserDetails.getUsername()));
    return ResponseEntity.ok(memberService.getExpLogs(command));
  }

  @PostMapping(value = "/rank/daily", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  @Override
  public ResponseEntity<MemberDto> getDailyMemberExpRankings(
      @ModelAttribute MemberCommand command) {
    return ResponseEntity.ok(memberRankingService.getDailyMemberExpRankings(command));
  }
}
