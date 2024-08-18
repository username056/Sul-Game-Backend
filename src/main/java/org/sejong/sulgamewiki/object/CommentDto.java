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
public class CommentDto {
  private Long id;
  private Comment comment;
  private Long memberId;
}
