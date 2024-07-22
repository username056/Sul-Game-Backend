package org.sejong.sulgamewiki.member.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.dto.request.MemberDto;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member createMember(MemberDto memberDto) {
    Member member = memberDto.toEntity();
    return memberRepository.save(member);
  }

  public Member getMember(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(()
            -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
  }

  public Member updateMember(Long id, MemberDto memberDto) {
    Member existingMember = memberRepository.findById(id)
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    existingMember.updateFromRequest(memberDto);
    return memberRepository.save(existingMember);
  }
  public void deleteMember(Long id) {
    Member existingMember = memberRepository.findById(id)
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    memberRepository.delete(existingMember);
  }
}
