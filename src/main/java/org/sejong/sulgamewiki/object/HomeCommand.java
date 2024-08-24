package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class HomeCommand {
  private List<OfficialGameCommand> officialGameList;
//  private List<CreativeGameCommand> creativeGameList;
  private List<IntroCommand> introList;
}
