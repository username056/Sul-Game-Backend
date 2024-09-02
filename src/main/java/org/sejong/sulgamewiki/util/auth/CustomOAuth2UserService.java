package org.sejong.sulgamewiki.util.auth;


import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.object.constants.Role;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
          entity.setNickname(attributes.getName());
          entity.setProfileUrl(attributes.getProfileImageUrl());
          entity.setLastLoginTime(LocalDateTime.now());
          return memberRepository.save(entity);
        })
        .orElseGet(() -> {
          Member newMember = Member.builder()
              .email(attributes.getEmail())
              .nickname(attributes.getName())
              .profileUrl(attributes.getProfileImageUrl())
              .accountStatus(AccountStatus.PENDING)
              .role(Role.ROLE_USER)  // 기본 역할 : ROLE_USER
              .build();
          log.info("신규 회원 생성됨 : memberId : {} , memberEmail : {}", newMember.getMemberId(), newMember.getEmail());
          return memberRepository.save(newMember);
        });
  }
}