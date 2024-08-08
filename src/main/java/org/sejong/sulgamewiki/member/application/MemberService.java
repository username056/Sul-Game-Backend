package org.sejong.sulgamewiki.member.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.dto.request.CompleteRegistrationRequest;
import org.sejong.sulgamewiki.member.dto.request.CreateMemberRequest;
import org.sejong.sulgamewiki.member.dto.request.UpdateMemberResponse;
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

  @Transactional
  public CreateMemberResponse completeRegistration(
      CompleteRegistrationRequest request) {
    Member member = memberRepository.findById(request.getMemberId())
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    if (member.getStatus() != MemberStatus.PENDING) {
      throw new MemberException(MemberErrorCode.INVALID_MEMBER_STATUS);
    }

    member.updateFromRequest(request);
    member.setStatus(MemberStatus.ACTIVE);
    Member savedMember = memberRepository.save(member);
    return CreateMemberResponse.from(savedMember);
  }

  public void deleteMember(Long memberId) {
    memberRepository.deleteById(memberId);
  }















//
//  public void updateMember(Long memberId, CreateMemberRequest createMemberRequest) {
//    Member member = this.memberRepository.findById(createMemberRequest.getMemberId()).orElseThrow();
//            memberRepository.update(Member.toEntity(createMemberRequest));
//        return ;
//  }


//  public CreaeteMemberResponse getMemberById(Long id) {
//    Member member = memberRepository.findById(id).orElseThrow(()
//        -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
//    return CreaeteMemberResponse.from(member);
//  }
//
//  public CreaeteMemberResponse updateMember(Long id, CreaeteMemberResponse creaeteMemberResponse) {
//
//    existingMember.updateFromRequest(creaeteMemberResponse);
//
//    Member updatedMember = memberRepository.save(existingMember);
//    return CreaeteMemberResponse.from(updatedMember);
//  }

//  }
}
