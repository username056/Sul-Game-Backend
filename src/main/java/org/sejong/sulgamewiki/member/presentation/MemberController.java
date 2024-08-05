package org.sejong.sulgamewiki.member.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.common.log.LogMonitoringInvocation;
import org.sejong.sulgamewiki.member.application.MemberService;
import org.sejong.sulgamewiki.member.dto.request.CompleteRegistrationRequest;
import org.sejong.sulgamewiki.member.dto.request.CreateMemberRequest;
import org.sejong.sulgamewiki.member.dto.response.CreateMemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
  private final MemberService memberService;

  @PostMapping// TODO: 이대로 수정하기
  @LogMonitoringInvocation
  public ResponseEntity<CreateMemberResponse> createMember(
      @RequestBody CreateMemberRequest createMemberRequest) {
    CreateMemberResponse createMemberResponse
        = memberService.createMember(createMemberRequest);
    return ResponseEntity.ok(createMemberResponse);
  }

  @PostMapping("/complete-registration")
  @LogMonitoringInvocation
  public ResponseEntity<CreateMemberResponse> completeRegistration(
       @RequestBody CompleteRegistrationRequest request
  ){
    log.debug(request.getUniversity());
    log.debug("Received request with memberId: {}", request.getMemberId());
    CreateMemberResponse response = memberService.completeRegistration(request);
    return ResponseEntity.ok(response);
  }

//
//  @GetMapping("/{id}")
//  public ResponseEntity<CreaeteMemberResponse> getMember(
//      @PathVariable Long id) {
//    CreaeteMemberResponse creaeteMemberResponse
//        = memberService.getMemberById(id);
//    return ResponseEntity.ok(creaeteMemberResponse);
//  }
//
//  @DeleteMapping("/{id}")
//  public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
//    memberService.deleteMember(id);
//    return ResponseEntity.noContent().build();
//  }

}
