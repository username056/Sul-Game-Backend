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
  private BaseMedia introMediaFileInGame;
  private Boolean isLiked;
  private Boolean isBookmarked;
  private Boolean isIntroExist;

  private List<CreationGame> relatedCreationGames;
  // 이거 전부 가져오는게 맞나...? 이름이랑 썸네일 정도만 있어도 될 것 같은데

  private List<OfficialGame> officialGames;

}
