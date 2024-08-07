package org.sejong.sulgamewiki.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode{
  INTERNAL_SERVER_ERROR("서버에 오류가 발생하였습니다.", HttpStatus.NOT_FOUND);

  private final String message;
  private final HttpStatus status;

}
