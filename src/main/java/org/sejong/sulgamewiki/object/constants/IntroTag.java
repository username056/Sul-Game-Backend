package org.sejong.sulgamewiki.object.constants;

import org.sejong.sulgamewiki.object.Intro;

public enum IntroTag {
  TAG1("이성과 하기 좋아요"),
  TAG2("빨리 취해요"),
  TAG3("움직여야 해요"),
  TAG4("머리를 써야 해요"),
  TAG5("스킨쉽 있어요"),
  TAG6("엄청 빨라요"),
  TAG7("차분해요"),
  TAG8("금방 끝나요"),
  TAG9("오래 걸려요"),
  TAG10("눈치 챙겨요"),
  TAG11("센스가 필요해요"),
  TAG12("도구가 필요해요"),
  TAG13("저격할 수 있어요"),
  TAG14("텐션 올릴 때 좋아요");

  private final String description;

  IntroTag(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
