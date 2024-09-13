package org.sejong.sulgamewiki.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SearchCommand {
  // 검색하는 문자열
  String query;
}
