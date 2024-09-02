package org.sejong.sulgamewiki.util.auth;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.object.constants.Role;

@Getter
public class OAuthAttributes {

  private final Map<String, Object> attributes;
  private final String name;
  private final String email;
  private final String profileImageUrl;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String name, String email, String profileImageUrl) {
    this.attributes = attributes;
    this.name = name;
    this.email = email;
    this.profileImageUrl = profileImageUrl;
  }

  public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
    if ("kakao".equals(registrationId)) {
      return ofKakao(attributes);
    } else if ("google".equals(registrationId)) {
      return ofGoogle(attributes);
    } else if ("naver".equals(registrationId)) {
      return ofNaver(attributes);
    }
    return null;
  }

  private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .profileImageUrl((String) attributes.get("picture"))
        .attributes(attributes)
        .build();
  }

  private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

    return OAuthAttributes.builder()
        .name((String) profile.get("nickname"))
        .email((String) kakaoAccount.get("email"))
        .profileImageUrl((String) profile.get("profile_image_url"))
        .attributes(attributes)
        .build();
  }

  private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return OAuthAttributes.builder()
        .name((String) response.get("nickname"))
        .email((String) response.get("email"))
        .profileImageUrl((String) response.get("profile_image"))
        .attributes(response)
        .build();
  }
}

