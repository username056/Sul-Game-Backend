package org.sejong.sulgamewiki.object;

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
  String googleRedirectUrl;
  AccountStatus loginAccountStatus;
  String accessToken;
  String refreshToken;
}
