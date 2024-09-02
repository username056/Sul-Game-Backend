package org.sejong.sulgamewiki.util.auth;


import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.AuthCommand;
import org.sejong.sulgamewiki.object.AuthDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.object.constants.Role;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * CustomOAuth2UserService 클래스는 OAuth2 사용자 정보를 처리하기 위해 커스터마이징된 서비스입니다.
 * 이 클래스는 사용자가 소셜 로그인으로 인증할 때 호출되며, 사용자의 정보를 가져와 기존 회원 정보와 비교하여
 * 업데이트하거나, 새로운 회원 정보를 데이터베이스에 저장합니다.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final MemberRepository memberRepository;

  /**
   * OAuth2 사용자 정보를 로드합니다.
   *
   * 이 메서드는 소셜 로그인 후 OAuth2 사용자 정보를 로드하고, 해당 정보를 바탕으로
   * 회원 정보를 업데이트하거나 새롭게 저장합니다. 그런 다음 CustomUserDetails 객체를 반환합니다.
   *
   * @param userRequest OAuth2UserRequest 객체로, 클라이언트와의 OAuth2 인증 요청 정보를 포함합니다.
   * @return CustomUserDetails OAuth2 사용자 정보를 포함하는 CustomUserDetails 객체를 반환합니다.
   * @throws OAuth2AuthenticationException OAuth2 인증 과정에서 예외가 발생할 경우 발생합니다.
   */
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    AuthDto dto = OAuth2AttributeConverter.of(
        AuthCommand.builder()
            .registrationId(userRequest.getClientRegistration().getRegistrationId())
            .attributes(oAuth2User.getAttributes())
            .build()
    );
    return new CustomUserDetails(saveOrUpdate(dto), dto.getAttributes());
  }

  /**
   * 회원 정보를 저장하거나 업데이트합니다.
   *
   * 이 메서드는 제공된 이메일로 기존 회원 정보를 검색하고,
   * 회원이 존재하면 닉네임, 프로필 이미지, 마지막 로그인 시간, 제공자 정보를 업데이트합니다.
   * 회원이 존재하지 않으면 새 회원을 생성하여 데이터베이스에 저장합니다.
   *
   * @param dto OAuth2 인증으로부터 얻은 사용자 정보를 포함한 AuthDto 객체입니다.
   * @return 업데이트되거나 새로 생성된 회원 객체를 반환합니다.
   */
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