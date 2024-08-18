package org.sejong.sulgamewiki.util.auth.object;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ResourceOfKakao{
  private Map<String, String> properties;
}
