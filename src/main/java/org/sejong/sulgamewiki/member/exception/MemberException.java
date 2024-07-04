package org.sejong.sulgamewiki.member.exception;

import org.sejong.sulgamewiki.common.exception.CustomException;
import org.sejong.sulgamewiki.common.exception.ErrorCode;

public class MemberException extends CustomException {

  public MemberException(ErrorCode errorCode) {
    super(errorCode, "member");
  }
}
