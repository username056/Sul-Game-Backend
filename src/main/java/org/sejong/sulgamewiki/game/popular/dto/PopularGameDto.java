package org.sejong.sulgamewiki.game.popular.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sejong.sulgamewiki.game.popular.domain.constants.GameStatus;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PopularGameDto {

  private Long id;
  private String title;
  private String description;
  private String developer;
  private GameStatus status;

  public static PopularGameDto fromEntity(PopularGame popularGame) {
    return new PopularGameDto(
        popularGame.getId(),
        popularGame.getTitle(),
        popularGame.getDescription(),
        popularGame.getDeveloper(),
        popularGame.getStatus()
    );
  }
}
