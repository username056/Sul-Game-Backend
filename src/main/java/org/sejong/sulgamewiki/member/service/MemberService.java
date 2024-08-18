package org.sejong.sulgamewiki.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.auth.domain.CustomUserDetails;
import org.sejong.sulgamewiki.member.object.MemberCommand;
import org.sejong.sulgamewiki.member.object.MemberDto;
import org.sejong.sulgamewiki.member.object.MemberStatus;
import org.sejong.sulgamewiki.member.object.Member;
import org.sejong.sulgamewiki.member.object.MemberRepository;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
  public CustomUserDetails loadUserByUsername(MemberCommand memberCommand) throws UsernameNotFoundException {
    Member member = memberRepository.findById(Long.parseLong(memberCommand.getMemberStringId()))
        .orElseThrow(
            () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    return new CustomUserDetails(member);
  }

  public MemberDto createMember(
      MemberCommand memberCommand) {
    Member member = Member.toEntity(createMemberRequest);
    Member savedMember = memberRepository.save(member);

    MemberDto memberDto = MemberDto.builder().build();
    memberDto.setMember(savedMember);
    return memberDto;
  }

  @Transactional
  public MemberDto completeRegistration(
      MemberCommand memberCommand) {
    Member member = memberRepository.findById(memberCommand.getMemberId())
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    if (member.getAcountStatus() != MemberStatus.PENDING) {
      throw new MemberException(MemberErrorCode.INVALID_MEMBER_STATUS);
    }

    member.updateFromRequest(request);
    member.setNickName(memberCommand.getNickName());
    member.setStatus(MemberStatus.ACTIVE);
    Member savedMember = memberRepository.save(member);
    MemberDto memberDto = MemberDto.builder().build();
    memberDto.setMember(member);
    return memberDto;
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
