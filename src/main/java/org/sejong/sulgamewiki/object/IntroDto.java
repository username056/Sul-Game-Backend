package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.IntroType;

@Builder
@Getter
@Setter
@ToString
public class IntroDto {
  private Long introId;
  private Long memberId;
  private String title;
  private String description;
  private String lyrics;
  private IntroType type;
  private BasePost basePost;
  private List<BaseMedia> baseMedias;
}
