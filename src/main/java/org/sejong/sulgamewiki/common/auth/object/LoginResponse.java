package org.sejong.sulgamewiki.common.auth.object;

import lombok.Builder;
import org.sejong.sulgamewiki.common.jwt.dto.TokenResponse;

@Builder
public class LoginResponse{
  private PlayerProfile profile;
  private TokenResponse token;
}
