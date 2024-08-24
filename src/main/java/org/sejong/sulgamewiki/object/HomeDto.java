package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class HomeDto {
  /*
  최신게시물 리스트
  국룰술게임(공식술게임) 리스트
  실시간차트 리스트
  인트로자랑하기 리스트
  오늘 가장 핫했던 술게임 리스트
   */
  private List<BasePostDto> latestPosts;
  private List<OfficialGameDto> officialGames;
  private List<BasePostDto> realTimeChart;
  private List<IntroDto> introPride;
  private List<BasePostDto> hotGamesToday;
}
