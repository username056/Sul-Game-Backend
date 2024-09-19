package org.sejong.sulgamewiki.object.constants;

public enum HeadCountTag {
  HTAG1("관계 없음"),
  HTAG2("최소 4인 이상");

  private final String description;
  HeadCountTag(String description) {
    this.description = description;
  }
  public String getDescription() {
    return description;
  }
}

