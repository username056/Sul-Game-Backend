package org.sejong.sulgamewiki.comment.domain.entity;

import lombok.Getter;

@Getter
public enum CommentType {
  POPULAR_GAME("인기게임 게시물"),
  CREATIVE_GAME("창작게임 게시물"),
  INTRO_GAME("인트로 게시물");

  private final String description;

  CommentType(String description) {
    this.description = description;
  }
}
