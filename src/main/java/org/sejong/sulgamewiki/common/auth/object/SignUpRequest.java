package org.sejong.sulgamewiki.common.auth.object;

import jakarta.validation.constraints.NotBlank;

public class SignUpRequest{
  private @NotBlank String name;
  private @NotBlank String email;
  private @NotBlank String password;
}
