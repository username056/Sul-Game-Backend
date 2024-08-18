package org.sejong.sulgamewiki.util.auth.object;

import lombok.Builder;
import org.sejong.sulgamewiki.util.TokenResponse;

@Builder
public class LoginResponse{
  private PlayerProfile profile;
  private TokenResponse token;
}
