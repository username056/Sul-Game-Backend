package org.sejong.sulgamewiki.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.service.MemberService;
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
  public ResponseEntity<MemberDto> createMember(
      @RequestBody MemberCommand memberCommand) {

    MemberDto memberDto
        = memberService.createMember(memberCommand);
    return ResponseEntity.ok(memberDto);
  }

  @PostMapping("/complete-registration")
  @LogMonitoringInvocation
  public ResponseEntity<MemberDto> completeRegistration(
       @RequestBody MemberCommand memberCommand
  ){
    MemberDto memberDto = memberService.completeRegistration(memberCommand);
    return ResponseEntity.ok(memberDto);
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
