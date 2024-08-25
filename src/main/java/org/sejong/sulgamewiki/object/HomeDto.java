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
  최신술게임
   */
  private List<CreationGame> latestCreationList;
  private List<Intro> latestIntroList;

  /*
  국룰술게임
   */
  private List<OfficialGame> officialGames;

  /*
  실시간 ㅅㄱㅇㅋ차트
   */
  private List<CreationGame> creationGameDailyChart;
  private List<Intro> introDailyChart;
  private List<OfficialGame> officialGameDailyChart;

  /*
  인트로 자랑하기
   */
  private List<Intro> introsByLikes;
  private List<Intro> introsByViews;

  /*
  오늘 가장 핫했던 술게임
   */
  private List<BasePost> weeklyHotGames;
}
