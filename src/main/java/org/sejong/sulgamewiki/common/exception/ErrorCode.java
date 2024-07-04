package org.sejong.sulgamewiki.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  String getMessage();
  HttpStatus getStatus();
}
