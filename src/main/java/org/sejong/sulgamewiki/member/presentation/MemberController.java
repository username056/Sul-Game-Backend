package org.sejong.sulgamewiki.member.presentation;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.application.MemberService;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.dto.request.MemberDto;
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

  @PostMapping
  public ResponseEntity<Member> createMember(@RequestBody MemberDto memberDto) {
    Member member = memberService.createMember(memberDto);
    return ResponseEntity.ok(member);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Member> getMember(@PathVariable Long id) {
    Member member = memberService.getMember(id);
    return ResponseEntity.ok(member);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
    memberService.deleteMember(id);
    return ResponseEntity.noContent().build();
  }

}
