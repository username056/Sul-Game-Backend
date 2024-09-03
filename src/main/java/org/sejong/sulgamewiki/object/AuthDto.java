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
  // 로그인 성공 응답 데이터
  AccountStatus loginAccountStatus;
  String refreshToken;
  String nickname;

  // OAuthAttributes
  private final Map<String, Object> attributes;
  private final String name;
  private final String email;
  private final String profileImageUrl;
  private final String provider;

  private Member member;
}
