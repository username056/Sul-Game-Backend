package org.sejong.sulgamewiki.member.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.dto.response.MemberResponseDto;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

  private final MemberRepository memberRepository;

  public MemberResponseDto loadUserByUsername(String memberStringId) throws UsernameNotFoundException {
    Member member = memberRepository.findById(Long.parseLong(memberStringId))
        .orElseThrow(
            () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    return MemberResponseDto.from(member);
  }
}
