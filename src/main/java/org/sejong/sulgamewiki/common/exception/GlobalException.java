package org.sejong.sulgamewiki.common.exception;

public class GlobalException extends CustomException{

  public GlobalException(ErrorCode errorCode) {
    super(errorCode, "global");
  }
}
