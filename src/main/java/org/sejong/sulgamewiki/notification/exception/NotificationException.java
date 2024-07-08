package org.sejong.sulgamewiki.notification.exception;

import org.sejong.sulgamewiki.common.exception.CustomException;
import org.sejong.sulgamewiki.common.exception.ErrorCode;

public class NotificationException extends CustomException {
    public NotificationException(ErrorCode errorCode) {
        super(errorCode, "notification");
    }
}
