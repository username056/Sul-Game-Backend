package org.sejong.sulgamewiki.object;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.CommentType;

@Getter
@Builder
@ToString
public class CommentDto {
  private Long id;
  private String content;
  private Long typeId;
  private CommentType commentType;
  private Long memberId;
}
