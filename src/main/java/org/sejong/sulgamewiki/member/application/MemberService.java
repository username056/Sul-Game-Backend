package org.sejong.sulgamewiki.member.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.dto.request.CreateMemberRequest;
import org.sejong.sulgamewiki.member.dto.response.CreateMemberResponse;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public CreateMemberResponse createMember(
      CreateMemberRequest createMemberRequest) {
    Member member = Member.toEntity(createMemberRequest);
    Member savedMember = memberRepository.save(member);
    return CreateMemberResponse.from(savedMember);
  }

//  public CreaeteMemberResponse getMemberById(Long id) {
//    Member member = memberRepository.findById(id).orElseThrow(()
//        -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
//    return CreaeteMemberResponse.from(member);
//  }
//
//  public CreaeteMemberResponse updateMember(Long id, CreaeteMemberResponse creaeteMemberResponse) {
//    Member existingMember = memberRepository.findById(id)
//        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
//    existingMember.updateFromRequest(creaeteMemberResponse);
//
//    Member updatedMember = memberRepository.save(existingMember);
//    return CreaeteMemberResponse.from(updatedMember);
//  }
//  public void deleteMember(Long id) {
//    Member existingMember = memberRepository.findById(id)
//        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
//    memberRepository.delete(existingMember);
//  }
}
