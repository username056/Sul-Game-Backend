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
public class OAuth2AttributeConverter {

  /**
   * 주어진 소셜 로그인 제공자에 따라 사용자 정보를 AuthDto로 변환합니다.
   *
   * 이 메서드는 등록된 소셜 로그인 제공자 (Google, Kakao, Naver)에 따라
   * 각각의 제공자로부터 전달된 사용자 속성을 처리하여,
   * 공통의 AuthDto 객체로 변환합니다. 지원되지 않는 제공자인 경우,
   * CustomException을 발생시킵니다.
   *
   * @param command AuthCommand 객체로, 소셜 로그인 제공자의 ID와 사용자 속성을 포함합니다.
   * @return AuthDto 객체로 변환된 사용자 정보를 반환합니다.
   * @throws CustomException 지원되지 않는 소셜 로그인 제공자인 경우 예외가 발생합니다.
   */
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

