package org.sejong.sulgamewiki.util.auth;


import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.AuthCommand;
import org.sejong.sulgamewiki.object.AuthDto;
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

    AuthDto dto = OAuthAttributes.of(
        AuthCommand.builder()
            .registrationId(userRequest.getClientRegistration().getRegistrationId())
            .attributes(oAuth2User.getAttributes())
            .build()
    );

    return new CustomUserDetails(saveOrUpdate(dto), dto.getAttributes());
  }

  private Member saveOrUpdate(AuthDto dto) {
    return memberRepository.findByEmail(dto.getEmail())
        .map(entity -> {
          entity.setNickname(dto.getName());
          entity.setProfileUrl(dto.getProfileImageUrl());
          entity.setLastLoginTime(LocalDateTime.now());
          entity.setProvider(dto.getProvider());
          return memberRepository.save(entity);
        })
        .orElseGet(() -> {
          Member newMember = Member.builder()
              .email(dto.getEmail())
              .nickname(dto.getName())
              .profileUrl(dto.getProfileImageUrl())
              .accountStatus(AccountStatus.PENDING)
              .provider(dto.getProvider())
              .lastLoginTime(LocalDateTime.now())
              .role(Role.ROLE_USER)  // 기본 역할 : ROLE_USER
              .build();
          log.info("신규 회원 생성됨: 이메일 = {}, 제공자 = {}", newMember.getEmail(), dto.getProvider());
          return memberRepository.save(newMember);
        });
  }
}