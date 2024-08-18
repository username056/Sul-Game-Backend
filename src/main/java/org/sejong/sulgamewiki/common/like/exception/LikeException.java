package org.sejong.sulgamewiki.common.like.exception;

import lombok.Getter;
import org.sejong.sulgamewiki.common.exception.CustomException;

@Getter
public class LikeException extends CustomException {

  public LikeException(LikeErrorCode errorCode) {
    super(errorCode, "like");
  }
}
