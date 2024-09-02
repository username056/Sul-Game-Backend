package org.sejong.sulgamewiki.object;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.AccountStatus;

@Getter
@Setter
@Builder
@ToString
public class AuthDto {
  String redirectUrl;
  AccountStatus loginAccountStatus;
  String accessToken;
  String refreshToken;

  // OAuthAttributes
  private final Map<String, Object> attributes;
  private final String name;
  private final String email;
  private final String profileImageUrl;
  private final String provider;

}
