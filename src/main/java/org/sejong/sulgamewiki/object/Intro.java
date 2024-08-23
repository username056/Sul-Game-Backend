package org.sejong.sulgamewiki.object;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.object.constants.IntroType;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("Intro")
@ToString(callSuper = true)
public class Intro extends BasePost {
  @Column(length = 200)
  private String lyrics;

  private IntroType type;
}
