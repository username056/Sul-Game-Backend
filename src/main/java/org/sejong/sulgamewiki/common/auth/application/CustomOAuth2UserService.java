package org.sejong.sulgamewiki.common.auth.application;


import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.auth.domain.CustomUserDetails;
import org.sejong.sulgamewiki.common.entity.constants.MemberRole;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    String email = oAuth2User.getAttribute("email");
    Member member = memberRepository.findByEmail(email)
        .orElseGet(() -> createPendingMember(oAuth2User));
    return new CustomUserDetails(member, oAuth2User.getAttributes());
  }

  private Member createPendingMember(OAuth2User oAuth2User) {
    String email = oAuth2User.getAttribute("email");
    Member newMember = Member.builder()
        .email(email)
        .role(MemberRole.ROLE_USER)
        .status(MemberStatus.PENDING)
        .build();
    return memberRepository.save(newMember);
  }
}