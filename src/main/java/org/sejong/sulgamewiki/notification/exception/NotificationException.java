package org.sejong.sulgamewiki.notification.exception;

import org.sejong.sulgamewiki.common.exception.CustomException;
import org.sejong.sulgamewiki.common.exception.constants.ErrorSource;
import org.springframework.http.HttpStatus;

public class NotificationException extends CustomException {
    private final NotificationErrorCode errorCode;

    public NotificationException(NotificationErrorCode errorCode) {
        super(errorCode, errorCode.getErrorSource().name());
        this.errorCode = errorCode;
    }

    public NotificationErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }

    public ErrorSource getErrorSource() {
        return errorCode.getErrorSource();
    }
}