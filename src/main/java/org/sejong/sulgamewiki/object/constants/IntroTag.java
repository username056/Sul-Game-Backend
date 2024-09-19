package org.sejong.sulgamewiki.object.constants;

import org.sejong.sulgamewiki.object.Intro;

public enum IntroTag {
  TAG1("흥겨워요!"),
  TAG2("움직여야해요!"),
  TAG3("차분해요!"),
  TAG4("엄청빨라요!"),
  TAG5("텐션올릴때좋아요!"),
  TAG6("분위기를바꿔요!"),
  TAG7("센스가필요해요!"),
  TAG8("술겜끝나고!"),
  TAG9("술겜시작전!"),
  TAG10("술겜중간에!");

  private final String description;

  IntroTag(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
