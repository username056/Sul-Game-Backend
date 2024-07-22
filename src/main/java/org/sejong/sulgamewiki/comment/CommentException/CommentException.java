package org.sejong.sulgamewiki.comment.CommentException;

import org.sejong.sulgamewiki.common.exception.CustomException;
import org.sejong.sulgamewiki.common.exception.ErrorCode;

public class CommentException extends CustomException {
    public CommentException(ErrorCode errorCode, String source) {
        super(errorCode, "comment");
    }
}
