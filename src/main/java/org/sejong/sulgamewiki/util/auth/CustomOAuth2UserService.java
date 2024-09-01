package org.sejong.sulgamewiki.util.auth;


import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.MemberRepository;
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

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    Map<String, Object> attributes = oAuth2User.getAttributes();

    OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, attributes);

    Member member = saveOrUpdate(oAuthAttributes);

    return new CustomUserDetails(member, attributes);
  }

  private Member saveOrUpdate(OAuthAttributes attributes) {
    return memberRepository.findByEmail(attributes.getEmail())
        .map(entity -> {
          entity.update(attributes.getName(), attributes.getProfileImageUrl());
          entity.setLastLoginTime(LocalDateTime.now());  // lastLoginTime 설정
          return memberRepository.save(entity);
        })
        .orElseGet(() -> {
          Member newMember = attributes.toEntity();
          newMember.setLastLoginTime(LocalDateTime.now());  // lastLoginTime 설정
          return memberRepository.save(newMember);
        });
  }
}