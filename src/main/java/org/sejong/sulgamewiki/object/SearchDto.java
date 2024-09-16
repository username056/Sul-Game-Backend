package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SearchDto {
  private List<Intro> intros;
  private List<OfficialGame> officialGames;
  private List<CreationGame> creationGames;
}
