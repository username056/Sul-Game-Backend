package org.sejong.sulgamewiki.object;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.object.constants.GameTag;
import org.sejong.sulgamewiki.object.constants.HeadCountTag;
import org.sejong.sulgamewiki.object.constants.LevelTag;
import org.sejong.sulgamewiki.object.constants.NoiseLevelTag;

@Entity
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("OfficialGame")
public class OfficialGame extends BasePost {

  // 150
  @Size(max = 150, message = "게임 내 인트로 150자 제한")
  private String introLyricsInGamePost;

  private String introMediaFileInGamePostUrl;

  private Boolean isIntroExist;

  private LevelTag levelTag;
  private HeadCountTag headCountTag;
  private NoiseLevelTag noiseLevelTag;

  // 공식 술게임의 태그
  // TODO: 4개 이하로 막는 로직
  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<GameTag> gameTags = new HashSet<>();
}