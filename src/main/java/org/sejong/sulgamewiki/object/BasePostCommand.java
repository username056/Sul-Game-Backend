package org.sejong.sulgamewiki.object;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.IntroType;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@ToString
public class BasePostCommand {
  private Long basePostId;
  private Long memberId;
  private SourceType sourceType;
  private BasePost basePost;

  // official, creation
  private String introduction;
  private String title;
  private String description;
  @Builder.Default
  private List<MultipartFile> multipartFiles = new ArrayList<>();
  private List<String> imageUrls;

  // intro
  private String lyrics;
  private IntroType introtype;
}
