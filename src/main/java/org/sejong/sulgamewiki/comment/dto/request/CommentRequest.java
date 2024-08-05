package org.sejong.sulgamewiki.comment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.sejong.sulgamewiki.comment.domain.entity.CommentType;

@Getter
@Setter
@Builder
@ToString
public class CommentRequest {
    private String content;
    private Long typeId;
    private CommentType commentType;
    private Long memberId;
}
