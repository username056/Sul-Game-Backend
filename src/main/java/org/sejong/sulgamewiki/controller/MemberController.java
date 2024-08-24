package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.service.MemberService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원 관리 API", description = "회원 관리 API 제공")
public class MemberController {
  private final MemberService memberService;

  @PostMapping("/complete-registration")
  @LogMonitoringInvocation
  @Operation(summary = "회원 등록 완료", description = "소셜로그인을 이후에 나머지 회원가입 완료")
  public ResponseEntity<MemberDto> completeRegistration(
       @RequestBody MemberCommand memberCommand
  ){
    MemberDto memberDto = memberService.completeRegistration(memberCommand);
    return ResponseEntity.ok(memberDto);
  }
}
