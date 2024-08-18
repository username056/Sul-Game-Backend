package org.sejong.sulgamewiki.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class OfficialGameDto {
  private Long popularGameId;
  private Long memberId;
  private String title;
  private String introduction;
  private String description;
  private int likes;
  private int views;
  private String mediaUrl;
  private String author;
  private String university;
}
