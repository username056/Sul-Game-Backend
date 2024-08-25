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

  LIKE_CANNOT_BE_UNDER_ZERO(HttpStatus.BAD_REQUEST, "좋아요는 0보다 작을수 없습니다."),

  NO_LIKE_TO_CANCEL(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않은 회원입니다."),

  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),

  // Member

  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),

  INVALID_ACCOUNT_STATUS(HttpStatus.BAD_REQUEST, "회원 상태가 정상이 아닙니다."),

  MOCK_MEMBER_MAPPING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "사이트에서 가짜회원정보를 가져오는도중 문제가 발생하였습니다."),

  // Intro

  // Game

  GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "게임이 존재하지 않습니다."),

  // Comment
  COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),

  COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "댓글의 작성자가 아닙니다."),

  // S3
  INVALID_MEDIA_TYPE(HttpStatus.BAD_REQUEST, "지원되지 않는 MIME 타입입니다"),

  // Auth

  // Notification

  NOTIFICATION_NOT_FOUND( HttpStatus.NOT_FOUND, "알림이 존재하지 않습니다."),

  INVALID_NOTIFICATION_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 알림 요청입니다."),

  INVALID_SOURCE_TYPE(HttpStatus.NOT_FOUND, "잘못된 소스 타입입니다."),

  // Report

  REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "신고내역이 존재하지 않습니다."),

  ALREADY_REPORTED(HttpStatus.BAD_REQUEST, "이미 신고했습니다.");

  private final HttpStatus status;
  private final String message;
}