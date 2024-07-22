package org.sejong.sulgamewiki.member.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.dto.request.MemberRequest;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member createMember(MemberRequest memberRequest) {
    Member member = memberRequest.toEntity();
    return memberRepository.save(member);
  }

  public Member getMember(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(()
            -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
  }

  public Member updateMember(Long id, MemberRequest memberRequest) {
    Member existingMember = memberRepository.findById(id)
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    existingMember.updateFromRequest(memberRequest);
    return memberRepository.save(existingMember);
  }

}
