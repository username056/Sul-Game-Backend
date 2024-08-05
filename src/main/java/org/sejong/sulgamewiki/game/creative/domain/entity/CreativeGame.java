package org.sejong.sulgamewiki.game.creative.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.common.entity.BasePost;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;

@ToString
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("CreativeGame")
public class CreativeGame extends BasePost {

  @Column(length = 90)
  private String introduction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "popular_game_id")
  private PopularGame popularGame;
}