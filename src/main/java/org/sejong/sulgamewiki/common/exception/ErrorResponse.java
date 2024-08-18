package org.sejong.sulgamewiki.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sejong.sulgamewiki.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private ErrorCode errorCode;
  private String errorMessage;
}
