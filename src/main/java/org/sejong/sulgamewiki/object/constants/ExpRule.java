package org.sejong.sulgamewiki.object.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ExpRule enum은 사용자의 활동에 따른 경험치 변동을 정의합니다.
 * 각 규칙에는 부여되거나 차감되는 경험치와 해당 규칙의 설명이 포함됩니다.
 */
@AllArgsConstructor
@Getter
public enum ExpRule {
  // 로그인 및 기본 활동
  LOGIN_FIRST_TIME(50L, "오늘 처음 로그인 시 50 경험치 부여"),
  DAILY_LOGIN(10L, "매일 로그인 시 10 경험치 부여"),

  // 게시글 관련
  POST_CREATION(20L, "게시글 작성 시 20 경험치 부여"),
  POST_LIKE_GIVEN(5L, "게시글에 좋아요를 받으면 5 경험치 부여"),
  POST_LIKE_CANCELED(-5L, "게시글에 좋아요가 취소되면 5 경험치 회수"),
  POST_LIKE_GIVE(1L, "게시글에 좋아요를 누르면 1 경험치 부여"),
  POST_LIKE_CANCEL(-1L, "게시글에 좋아요를 취소하면 1 경험치 회수"),
  POST_DELETION(-20L, "게시글 삭제 시 20 경험치 회수"),

  // 댓글 관련
  COMMENT_CREATION(2L, "댓글 작성 시 2 경험치 부여"),
  COMMENT_LIKE_RECEIVED(1L, "댓글에 좋아요를 받으면 1 경험치 부여"),
  COMMENT_LIKE_CANCELED(1L, "댓글에 좋아요가 취소되면 1 경험치 회수"),
  COMMENT_DELETION(-2L, "댓글 삭제 시 2 경험치 회수"),

  // 신고 관련
  REPORT_FILED(1L, "신고 접수 시 1 경험치 부여"),
  REPORT_ACCEPTED(10L, "신고가 받아들여지면 10 경험치 부여"),
  REPORT_REJECTED(-1L, "신고가 거절되면 1 경험치 회수"),

  // 관리자 관련 (임의값 0)
  ADMIN_EXP_ADJUSTMENT(0L, "관리자에 의한 경험치 조정"),

  // 기타 활동 관련
  FRIEND_INVITATION(30L, "친구 초대 시 30 경험치 부여"),
  EVENT_PARTICIPATION(20L, "이벤트 참여 시 20 경험치 부여"),

  // 패널티 및 부정 행위 관련
  PENALTY_SPAM_POST(-50L, "스팸 게시글로 판명될 시 50 경험치 회수"),
  PENALTY_OBSCENE_CONTENT(-100L, "음란물 게시 시 100 경험치 회수"),
  PENALTY_HARASSMENT(-75L, "괴롭힘 행위가 신고될 시 75 경험치 회수");

  private final Long exp;
  private final String description;
}
