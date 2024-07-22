package org.sejong.sulgamewiki.comment.CommentException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
  COMMENT_NOT_FOUND("댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  ACCESS_DENIED("댓글의 작성자가 아닙니다.", HttpStatus.FORBIDDEN);

  private final String message;
  private final HttpStatus status;
}
