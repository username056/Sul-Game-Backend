package org.sejong.sulgamewiki.object.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SourceType {
  /*
  해당 객체가 어떤 객체인지 알려줌으로써 여러가지 api에서 활용될 ENUM 변수들
   */
  INTRO("인트로 게시물"),
  OFFICIAL_GAME("공식게임 게시물"),
  CREATION_GAME("창작게임 게시물"),
  COMMENT("댓글"),
  GAMES("게임(공식,창작 둘다 포함)"),  //공식게임과 창작게임 모두를 받아야할때 사용
  MEMBER("멤버");

  private final String description;  // 설명용
}
