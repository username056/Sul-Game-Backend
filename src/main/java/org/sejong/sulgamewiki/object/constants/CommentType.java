package org.sejong.sulgamewiki.object.constants;

import lombok.Getter;

@Getter
public enum CommentType {
  OFFICIAL_GAME("공식게임 게시물"),
  CREATION_GAME("창작게임 게시물"),
  INTRO_GAME("인트로 게시물");

  private final String description;

  CommentType(String description) {
    this.description = description;
  }
}
