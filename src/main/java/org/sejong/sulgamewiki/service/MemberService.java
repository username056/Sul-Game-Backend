package org.sejong.sulgamewiki.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.repository.MemberContentInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.auth.domain.CustomUserDetails;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {
  private final MemberRepository memberRepository;
  private final MemberContentInteractionRepository memberContentInteractionRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String stringMemberId)
      throws UsernameNotFoundException {
    Member member = memberRepository.findById(Long.parseLong(stringMemberId))
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    return new CustomUserDetails(member);
  }

  @Transactional
  public MemberDto completeRegistration(MemberCommand memberCommand) {
    MemberDto dto = MemberDto.builder().build();

    Member member = memberRepository.findById(memberCommand.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (member.getAccountStatus() != AccountStatus.PENDING) {
      log.error("회원 AccountStatus 비정상 : {}", member.getAccountStatus());
      throw new CustomException(ErrorCode.INVALID_ACCOUNT_STATUS);
    }

    member.setBirthDate(memberCommand.getBirthDate());
    member.setCollege(memberCommand.getUniversity());
    member.setIsUniversityPublic(memberCommand.getIsUniversityVisible());
    member.setIsNotificationEnabled(memberCommand.getIsNotiEnabled());
    member.setAccountStatus(AccountStatus.ACTIVE);

    Member updatedMember = memberRepository.save(member);

    dto.setMember(updatedMember);
    return dto;
  }

  public void deleteMember(MemberCommand command) {
    memberRepository.deleteById(command.getMemberId());
  }
}
