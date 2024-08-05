package org.sejong.sulgamewiki.common.like.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LikeErrorCode implements ErrorCode {
  LIKE_CANNOT_BE_UNDER_ZERO("좋아요는 음수일 수 없습니다.", HttpStatus.BAD_REQUEST),
  INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus status;
}
