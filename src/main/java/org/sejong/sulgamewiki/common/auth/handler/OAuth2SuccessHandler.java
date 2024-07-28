package org.sejong.sulgamewiki.common.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.auth.application.JwtUtil;
import org.sejong.sulgamewiki.common.auth.domain.CustomUserDetails;
import org.sejong.sulgamewiki.common.entity.constants.MemberRole;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final MemberRepository memberRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    String email = oAuth2User.getAttribute("email");

    Member member = memberRepository.findByEmail(email)
        .orElseGet(() -> createNewMember(oAuth2User));

    if (member.getStatus() == MemberStatus.PENDING) {
      handlePendingMember(response, member);
    } else {
      handleExistingMember(response, member);
    }
  }

  private Member createNewMember(OAuth2User oAuth2User) {
    String email = oAuth2User.getAttribute("email");
    Member newMember = Member.builder()
        .email(email)
        .role(MemberRole.ROLE_USER)
        .status(MemberStatus.PENDING)
        .build();
    return memberRepository.save(newMember);
  }

  private void handlePendingMember(HttpServletResponse response, Member member) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"status\":\"PENDING\",\"memberId\":\"" + member.getId() + "\"}");
  }

  private void handleExistingMember(HttpServletResponse response, Member member) throws IOException {
    CustomUserDetails userDetails = new CustomUserDetails(member);
    String token = jwtUtil.createAccessToken(userDetails);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"token\":\"" + token + "\"}");
  }
}
