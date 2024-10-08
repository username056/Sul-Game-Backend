package org.sejong.sulgamewiki.object.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScoreRule {
  // 게시글 관련
  UP_LIKE(10, "해당 게시글 좋아요를 받으면 10 포인트 부여"),
  VIEW(1, "해당 게시글 조회하면 1 포인트 부여"),
  WRITE_COMMENT(2, "해당 게시글에 댓글이 달리면 2 포인트 부여"),
  BOOKMARK(5, "해당 게시글을 즐겨찾기하면 5 포인트 부여"),
  DOWN_LIKE(10, "해당 게시글 좋아요를 취소하면 -10 포인트 부여"),
  DELETE_COMMENT(-2, "해당 게시글에 댓글을 삭제하면 2 포인트 회수"),
  CANCEL_BOOKMARK(-5, "해당 게시글을 즐겨찾기 취소하면 5 포인트 회수"),
  REPORTED(-50, "해당 게시글이 신고되면 50 포인트 회수"),

  // 관리자 관련 (임의값 0)
  ADMIN_EXP_ADJUSTMENT(0, "관리자에 의한 경험치 조정");

  private final int score;
  private final String description;
}
