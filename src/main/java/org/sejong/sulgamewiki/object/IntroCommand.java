package org.sejong.sulgamewiki.object;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class IntroCommand {
  @Schema(description = "노래의 제목", example = "술게임 시작 노래")
  private String title;

  @Schema(description = "노래에 대한 설명", example = "술게임을 시작할 때 부르는 노래입니다.")
  private String description;

  @Schema(description = "노래 가사", example = "술게임을 시작할 때 부르는 노래가사입니다.")
  private String lyrics;

}
