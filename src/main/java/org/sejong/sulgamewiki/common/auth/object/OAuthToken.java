package org.sejong.sulgamewiki.common.auth.object;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OAuthToken{
  private String access_token;
  private String refresh_token;
  private Long expires_in;
  private Long refresh_token_expires_in;
  private String scope;
  private String token_type;
  private String id_token;
}
