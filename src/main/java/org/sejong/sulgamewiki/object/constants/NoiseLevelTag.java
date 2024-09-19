package org.sejong.sulgamewiki.object.constants;

public enum NoiseLevelTag {

  TAG1("조용함"),
  TAG2("시끄러움"),
  TAG3("모르겠음");

  private final String description;
  NoiseLevelTag(String description) {
    this.description = description;
  }
  public String getDescription() {
    return description;
  }
}
