package org.sejong.sulgamewiki.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.IntroTag;
import org.sejong.sulgamewiki.object.constants.IntroType;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.object.constants.GameTag;
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
  private String thumbnailIcon;
  private Boolean isLiked;
  private Boolean isBookmarked;

  // official, creation
  private String introduction;
  private String title;
  private String description;
  @Builder.Default
  private List<MultipartFile> gameMultipartFiles = new ArrayList<>();
  private List<String> imageUrls;
  private Set<GameTag> gameTags;
  private String introLyricsInGame;
  private MultipartFile introMultipartFileInGame;
  private String IntroMediaUrlFromGame;

  // creation, intro
  private Boolean isCreatorInfoPrivate;
  private Long relatedOfficialGameId;

  // intro
  private String lyrics;
  private IntroType introType;
  private Set<IntroTag> introTags;
  // TODO: 오류 해결하기
  @Builder.Default
  private List<MultipartFile> introMultipartFiles = new ArrayList<>();
}
