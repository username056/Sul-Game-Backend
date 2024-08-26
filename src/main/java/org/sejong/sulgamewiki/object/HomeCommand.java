package org.sejong.sulgamewiki.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

@Builder
@Getter
@Setter
@ToString
public class HomeCommand {
  private Pageable pageable;
}
