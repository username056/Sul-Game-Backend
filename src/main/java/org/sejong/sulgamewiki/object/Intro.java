package org.sejong.sulgamewiki.object;

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
import org.sejong.sulgamewiki.object.constants.IntroTag;
import org.sejong.sulgamewiki.object.constants.IntroType;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@DiscriminatorValue("Intro")
@ToString(callSuper = true)
public class Intro extends BasePost {
  @Column(length = 200)
  private String lyrics;

  private IntroType introType;

  // 인트로의 태그
  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<IntroTag> introTags = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "official_game_id")
  private OfficialGame officialGame;
}
