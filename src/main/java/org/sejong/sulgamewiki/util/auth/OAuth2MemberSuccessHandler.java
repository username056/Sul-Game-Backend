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
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.object.constants.Role;
import org.sejong.sulgamewiki.util.JwtUtil;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.security.core.Authentication;
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

  @Override
  @LogMonitoringInvocation
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
    String email = null;
    String nickname = null;
    String picture = null;

    if ("google".equals(registrationId)) {
      email = oAuth2User.getAttribute("email");
      nickname = oAuth2User.getAttribute("name");
      picture = oAuth2User.getAttribute("picture");
    } else if ("kakao".equals(registrationId)) {
      Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
      email = (String) kakaoAccount.get("email");
      Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
      nickname = (String) profile.get("nickname");
      picture = (String) profile.get("profile_image_url");
    }

    log.info("소셜 로그인 성공 : {}",email);

    Member member = memberRepository.findByEmail(email)
        .orElseGet(() -> {
          String newEmail = oAuth2User.getAttribute("email");
          Member newMember = Member.builder()
              .email(newEmail)
              .nickname(oAuth2User.getAttribute("name"))
              .profileUrl(oAuth2User.getAttribute("picture"))
              .role(Role.ROLE_USER)
              .accountStatus(AccountStatus.PENDING)
              .build();
          return memberRepository.save(newMember);
        });

    // JWT 생성
    CustomUserDetails userDetails = new CustomUserDetails(member);
    String accessToken = jwtUtil.createAccessToken(userDetails);
    String refreshToken = jwtUtil.createRefreshToken(userDetails);

    log.info("AccessToken 생성됨: {}", accessToken);
    log.info("RefreshToken 생성됨 : {}", refreshToken);

    // accessToken을 헤더에 추가
    response.setHeader("Authorization", "Bearer " + accessToken);

    // 응답 데이터 (refreshToken은 Body에 추가)
    AuthDto dto =  AuthDto.builder()
        .loginAccountStatus(member.getAccountStatus())
        .refreshToken(refreshToken)
        .build();

    // JSON 응답
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(dto));
  }
}
