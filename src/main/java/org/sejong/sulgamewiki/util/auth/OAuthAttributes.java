package org.sejong.sulgamewiki.util.auth;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.AuthCommand;
import org.sejong.sulgamewiki.object.AuthDto;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;

@Getter
@Builder
@Slf4j
public class OAuthAttributes {

  public static AuthDto of(AuthCommand command) {
    if ("kakao".equals(command.getRegistrationId())) {
      return ofKakao(command);
    } else if ("google".equals(command.getRegistrationId())) {
      return ofGoogle(command);
    } else if ("naver".equals(command.getRegistrationId())) {
      return ofNaver(command);
    }
    log.error("소셜 로그인 제공자를 알수 없습니다 : registrationId = {}", command);
    throw new CustomException(ErrorCode.INVALID_REGISTRATION_ID);
  }

  private static AuthDto ofGoogle(AuthCommand command) {
    Map<String, Object> attributes = command.getAttributes();
    return AuthDto.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .profileImageUrl((String) attributes.get("picture"))
        .attributes(attributes)
        .provider("google")
        .build();
  }

  private static AuthDto ofKakao(AuthCommand command) {
    Map<String, Object> attributes = command.getAttributes();
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

    return AuthDto.builder()
        .name((String) profile.get("nickname"))
        .email((String) kakaoAccount.get("email"))
        .profileImageUrl((String) profile.get("profile_image_url"))
        .attributes(attributes)
        .provider("kakao")
        .build();
  }

  private static AuthDto ofNaver(AuthCommand command) {
    Map<String, Object> attributes = command.getAttributes();
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return AuthDto.builder()
        .name((String) response.get("nickname"))
        .email((String) response.get("email"))
        .profileImageUrl((String) response.get("profile_image"))
        .attributes(response)
        .provider("naver")
        .build();
  }
}

