package org.sejong.sulgamewiki.member.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.auth.domain.CustomUserDetails;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String memberStringId) throws UsernameNotFoundException {
    Member member = memberRepository.findById(Long.parseLong(memberStringId))
        .orElseThrow(
            () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    return new CustomUserDetails(member);
  }
}
