package org.sejong.sulgamewiki.game.popular.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;

@Getter
@Builder
@ToString
public class CreatePopularGameResponse {
  private Long popularGameId;
  private Long memberId;
  private String title;

  public static CreatePopularGameResponse from(PopularGame popularGame) {
    return CreatePopularGameResponse.builder()
        .popularGameId(popularGame.getId())
        .memberId(popularGame.getMember().getId())
        .title(popularGame.getTitle())
        .build();
  }
}
