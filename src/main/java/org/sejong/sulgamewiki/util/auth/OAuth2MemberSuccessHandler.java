package org.sejong.sulgamewiki.util.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.object.constants.Role;
import org.sejong.sulgamewiki.util.JwtUtil;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends
    SimpleUrlAuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final MemberRepository memberRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    String email = oAuth2User.getAttribute("email");

    Member member = memberRepository.findByEmail(email)
        .orElseGet(() -> createPendingMember(oAuth2User));

    if (member.getAccountStatus() == AccountStatus.PENDING) {
      handlePendingMember(response, member);
    } else {
      handleExistingMember(response, member);
    }
  }

  private Member createPendingMember(OAuth2User oAuth2User) {
    String email = oAuth2User.getAttribute("email");
    Member newMember = Member.builder()
        .email(email)
        .nickname(oAuth2User.getAttribute("name"))
        .profileUrl(oAuth2User.getAttribute("picture"))
        .role(Role.ROLE_USER)
        .accountStatus(AccountStatus.PENDING)
        .build();
    return memberRepository.save(newMember);
  }

  private void handlePendingMember(HttpServletResponse response, Member member) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"status\":\"PENDING\",\"memberId\":\"" + member.getMemberId() + "\"}");
  }

  private void handleExistingMember(HttpServletResponse response, Member member) throws IOException {
    CustomUserDetails userDetails = new CustomUserDetails(member);
    String accessToken = jwtUtil.createAccessToken(userDetails);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"token\":\"" + accessToken + "\"}");
  }
}
