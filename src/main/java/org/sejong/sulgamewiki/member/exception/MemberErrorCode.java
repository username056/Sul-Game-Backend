package org.sejong.sulgamewiki.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
  MEMBER_NOT_FOUND("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus status;
}