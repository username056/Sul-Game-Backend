package org.sejong.sulgamewiki.object.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortBy {
  CREATION_DATE("createdDate", "생성날짜순"),
  LIKES("likes", "좋아요순"),
  VIEWS("views", "조회수순"),
  DAILY_SCORE("dailyScore", "오늘의 인기순"),
  WEEKLY_SCORE("weeklyScore", "금주의 인기순");

  private final String value;  // 정렬에 사용할 필드명
  private final String description;  // 설명용
}
