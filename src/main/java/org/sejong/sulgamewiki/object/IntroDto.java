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
public class IntroDto {
  private Long introId;
  private Long memberId;
  private String title;
  private String description;
  private String lyrics;
  private BasePost basePost;
  private List<BaseMedia> baseMedias;
}
