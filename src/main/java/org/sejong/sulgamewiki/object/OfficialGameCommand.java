package org.sejong.sulgamewiki.object;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class OfficialGameCommand {
  private String title;
  private String description;
  private String introduction;
  private List<String> imageUrls;
}
