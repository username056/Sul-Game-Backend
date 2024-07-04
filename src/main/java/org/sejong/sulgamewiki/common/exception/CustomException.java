package org.sejong.sulgamewiki.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends RuntimeException{
  private final HttpStatus status;
  private final String source;

  public CustomException(ErrorCode errorCode, String source) {
    super(errorCode.getMessage());
    this.status = errorCode.getStatus();
    this.source = source;
  }
}
