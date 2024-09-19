package org.sejong.sulgamewiki.object.constants;

public enum LevelTag {
  LEVEL1("적당함"),
  LEVEL2("어려움");

  private final String description;
  LevelTag(String description) { this.description = description;}
  public String getDescription() {
    return description;
  }
}
