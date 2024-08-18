package org.sejong.sulgamewiki.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Global

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),

  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

  ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),

  // Member

  // Intro

  // Game

  GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "게임이 존재하지 않습니다."),

  // Comment
  COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),

  COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "댓글의 작성자가 아닙니다."),

  // S3

  // Auth

  // Notification

  NOTIFICATION_NOT_FOUND( HttpStatus.NOT_FOUND, "알림이 존재하지 않습니다."),

  INVALID_NOTIFICATION_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 알림 요청입니다.");

  private final HttpStatus status;
  private final String message;
}