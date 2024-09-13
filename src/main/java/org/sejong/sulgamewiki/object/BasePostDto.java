package org.sejong.sulgamewiki.object;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class BasePostDto {
  private BasePost basePost;
  private List<Intro> intros;
  private List<BaseMedia> baseMedias;
  private BaseMedia introMediaInGame;
  private OfficialGame officialGame;
  private CreationGame creationGame;
  private Map<Long, List<BaseMedia>> baseMediaMap;
  private BaseMedia introMediaFileInGamePost;
  private Boolean isLiked;
  private Boolean isBookmarked;

}
