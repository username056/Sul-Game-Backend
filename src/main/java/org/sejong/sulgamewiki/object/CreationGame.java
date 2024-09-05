package org.sejong.sulgamewiki.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
import org.springframework.web.multipart.MultipartFile;

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

  private String introLyrics;
  private String introMediaFileUrl;

  // 창작 술게임의 태그
  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<GameTag> gameTags = new HashSet<>();
}