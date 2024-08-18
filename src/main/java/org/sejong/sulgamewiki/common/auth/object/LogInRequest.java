package org.sejong.sulgamewiki.common.auth.object;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LogInRequest{
  @NotBlank String email;
  @NotBlank String password;
}
