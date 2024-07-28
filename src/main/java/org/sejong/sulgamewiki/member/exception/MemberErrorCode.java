package org.sejong.sulgamewiki.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
  MEMBER_NOT_FOUND("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  HAVE_TO_SIGN_IN("회원이 존재하지 않습니다. 회원가입이 필요합니다.", HttpStatus.NOT_FOUND),
  INVALID_MEMBER_STATUS("회원 상태가 잘못 되었습니다.", HttpStatus.BAD_REQUEST),
  INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus status;
}