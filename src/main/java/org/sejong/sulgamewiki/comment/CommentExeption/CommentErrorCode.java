package org.sejong.sulgamewiki.comment.CommentExeption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND("댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    MEMBER_NOT_FOUND("멤버가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
