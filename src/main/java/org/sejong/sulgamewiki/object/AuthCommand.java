package org.sejong.sulgamewiki.object;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AuthCommand {
  private String registrationId;
  private Map<String, Object> attributes;
  private String refreshToken;
}
