package org.sejong.sulgamewiki.common.auth.object;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Builder
@Getter
@ToString
public class ResourceOfNaver{
  private Map<String, String> response;
}
