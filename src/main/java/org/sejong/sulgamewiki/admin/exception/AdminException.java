package org.sejong.sulgamewiki.admin.exception;

import org.sejong.sulgamewiki.common.exception.CustomException;
import org.sejong.sulgamewiki.common.exception.ErrorCode;

public class AdminException extends CustomException {

    public AdminException(ErrorCode errorCode) {
        super(errorCode, "admin");
    }
}