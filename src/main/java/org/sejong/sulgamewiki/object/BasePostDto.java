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
public class BasePostDto {
  private BasePost basePost;
  private List<BaseMedia> baseMedias;
  private OfficialGame officialGame;
  private CreationGame creationGame;
}
