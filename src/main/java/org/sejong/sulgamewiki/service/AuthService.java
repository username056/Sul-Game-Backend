package org.sejong.sulgamewiki.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.AuthCommand;
import org.sejong.sulgamewiki.object.AuthDto;
import org.sejong.sulgamewiki.object.CustomUserDetails;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.JwtUtil;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final JwtUtil jwtUtil;
  private final MemberRepository memberRepository;

  @Transactional
  public AuthDto refreshAccessTokenByRefreshToken(AuthCommand command) {
    String refreshToken = command.getRefreshToken();

    if (!jwtUtil.validateToken(refreshToken)) {
      throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    String memberIdString = jwtUtil.getClaims(refreshToken).getSubject();
    log.info("헤더 리프레쉬 토큰 확인 : MEMBER ID: {}", memberIdString);
    Member member = memberRepository.findById(Long.parseLong(memberIdString))
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (!refreshToken.equals(member.getRefreshToken())) {
      throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
    }
    log.info("회원 리프레쉬 토큰과 일치합니다.");
    String newAccessToken = jwtUtil.createAccessToken(new CustomUserDetails(member));
    log.info("새로운 AccessToken을 발급합니다 : {}", newAccessToken);
    return AuthDto.builder()
        .accessToken(newAccessToken)
        .build();
  }
}
