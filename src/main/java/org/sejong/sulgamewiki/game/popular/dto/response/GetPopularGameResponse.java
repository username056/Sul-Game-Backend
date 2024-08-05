package org.sejong.sulgamewiki.game.popular.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;

@Getter
@Setter
@Builder
@ToString
public class GetPopularGameResponse {
  private long popularGameId;
  private String title;
  private String introduction;
  private String description;
  private int likes;
  private int views;
  private long memberId;

  public static GetPopularGameResponse from(PopularGame popularGame) {
    return GetPopularGameResponse.builder()
        .popularGameId(popularGame.getId())
        .title(popularGame.getTitle())
        .introduction(popularGame.getIntroduction())
        .description(popularGame.getDescription())
        .likes(popularGame.getLikes())
        .views(popularGame.getViews())
        .memberId(popularGame.getMember().getId())
        .build();
  }
}
