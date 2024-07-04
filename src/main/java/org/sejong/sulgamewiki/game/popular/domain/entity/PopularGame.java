package org.sejong.sulgamewiki.game.popular.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sejong.sulgamewiki.common.entity.BaseEntity;
import org.sejong.sulgamewiki.game.popular.domain.constants.GameStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PopularGame extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String developer;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private GameStatus status;

  // 기타 필요한 필드들 추가

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateDescription(String description) {
    this.description = description;
  }

  public void updateDeveloper(String developer) {
    this.developer = developer;
  }

  public void updateStatus(GameStatus status) {
    this.status = status;
  }
}

