package org.sejong.sulgamewiki.member.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;


}
