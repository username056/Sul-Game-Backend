package org.sejong.sulgamewiki.member.presentation;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.application.MemberService;
import org.sejong.sulgamewiki.member.dto.request.CreateMemberRequest;
import org.sejong.sulgamewiki.member.dto.response.CreateMemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @PostMapping// TODO: 이대로 수정하기
  public ResponseEntity<CreateMemberResponse> createMember(
      @RequestBody CreateMemberRequest createMemberRequest) {
    CreateMemberResponse createMemberResponse
        = memberService.createMember(createMemberRequest);
    return ResponseEntity.ok(createMemberResponse);
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
