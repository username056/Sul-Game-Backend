package org.sejong.sulgamewiki.common.auth.object;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class OAuthResource{
  private String id;
  private String email;
  private String name;
}