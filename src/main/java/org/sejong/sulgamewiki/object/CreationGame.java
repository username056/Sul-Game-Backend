package org.sejong.sulgamewiki.object;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.object.constants.GameTag;

@ToString(callSuper = true)
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("CreationGame")
public class CreationGame extends BasePost {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "official_game_id")
  private OfficialGame officialGame;

  // 150
  @Size(max = 150, message = "게임 내 인트로 150자 제한")
  private String introLyricsInGamePost;
  
  private String introMediaFileInGamePostUrl;

  // 창작 술게임의 태그
  // 4개 이하
  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<GameTag> gameTags = new HashSet<>();
}