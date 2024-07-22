package org.sejong.sulgamewiki.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.sejong.sulgamewiki.comment.domain.entity.CommentType;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private Long typeId;
    private CommentType commentType;
    private Long memberId;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .typeId(comment.getTypeId())
                .commentType(comment.getCommentType())
                .memberId(comment.getMember().getId())
                .build();
    }
}
