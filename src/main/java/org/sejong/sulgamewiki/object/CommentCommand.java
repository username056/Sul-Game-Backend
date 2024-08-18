package org.sejong.sulgamewiki.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.CommentType;

@Getter
@Setter
@Builder
@ToString
public class CommentCommand {
  private String content;
  private Long typeId;
  private CommentType commentType;
  private Long memberId;
}
