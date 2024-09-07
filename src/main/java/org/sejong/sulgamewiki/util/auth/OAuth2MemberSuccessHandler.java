package org.sejong.sulgamewiki.util.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.AuthDto;
import org.sejong.sulgamewiki.object.CustomUserDetails;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.JwtUtil;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final MemberRepository memberRepository;
  private final ObjectMapper objectMapper;

  /**
   * OAuth2 인증이 성공한 후 호출되는 메서드입니다.
   *
   * 이 메서드는 사용자가 Google 또는 Kakao를 통해 소셜 로그인을 성공적으로
   * 완료한 후에 호출됩니다. 사용자의 이메일을 통해 기존 회원 정보를 확인하고,
   * JWT를 생성하여 클라이언트에게 반환합니다. 인증된 사용자가 존재하지 않는 경우
   * 예외를 발생시킵니다.
   *
   * @param request  HttpServletRequest 객체로, 클라이언트의 요청 정보를 포함합니다.
   * @param response HttpServletResponse 객체로, 서버의 응답 정보를 포함합니다.
   * @param authentication 인증 객체로, 사용자 인증 정보를 포함합니다.
   * @throws IOException, ServletException 예외 발생 시 처리됩니다.
   */
  @Override
  @LogMonitoringInvocation
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    // OAuth2User 객체 -> 사용자 소셜 로그인 정보 추출
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    // 사용자가 로그인한 소셜 플랫폼의 식별자 추출
    String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

    // 소셜 로그인 제공자에 따라 이메일을 추출
    String email = extractEmailFromOAuth2User(oAuth2User, registrationId);

    log.info("소셜 로그인 성공: 이메일 = {}, 제공자 = {}", email, registrationId);

    // 유효한 회원인지 확인
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // JWT 생성
    CustomUserDetails userDetails = new CustomUserDetails(member);
    String accessToken = jwtUtil.createAccessToken(userDetails);
    String refreshToken = jwtUtil.createRefreshToken(userDetails);

    log.info("AccessToken 생성됨: {}", accessToken);
    log.info("RefreshToken 생성됨 : {}", refreshToken);

    // Refresh Token을 Member 엔티티에 저장
    member.setRefreshToken(refreshToken);
    memberRepository.save(member);

    // SecurityContextHolder에 CustomUserDetails 설정
    SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(userDetails, authentication.getAuthorities(), registrationId));

    // accessToken을 헤더에 추가
    response.setHeader("Authorization", "Bearer " + accessToken);

    // 응답 데이터 (refreshToken은 Body에 추가)
    AuthDto dto = AuthDto.builder()
        .loginAccountStatus(member.getAccountStatus())
        .refreshToken(refreshToken)
        .nickname(member.getNickname())
        .email(email)
        .build();

    // JSON 응답
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(dto));
  }

  private String extractEmailFromOAuth2User(OAuth2User oAuth2User, String registrationId) {
    if ("google".equals(registrationId)) {
      return oAuth2User.getAttribute("email");
    } else if ("kakao".equals(registrationId)) {
      Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
      return (String) kakaoAccount.get("email");
    }
    throw new CustomException(ErrorCode.INVALID_REGISTRATION_ID);
  }

}
