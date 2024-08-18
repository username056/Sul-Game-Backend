package org.sejong.sulgamewiki.util.auth.object;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LogInRequest{
  @NotBlank String email;
  @NotBlank String password;
}
