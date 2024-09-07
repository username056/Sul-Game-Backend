package org.sejong.sulgamewiki.object.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ExpLevel enum은 사용자 경험치 레벨을 정의합니다.
 * D가 가장 낮은 레벨이며, A가 가장 높은 레벨입니다.
 * 각 레벨에 대한 설명을 포함합니다.
 */
@AllArgsConstructor
@Getter
public enum ExpLevel {
  A("마스터 레벨 - 탁월한 경험치"),
  B("고급 레벨 - 우수한 경험치"),
  C("중급 레벨 - 보통의 경험치"),
  D("초급 레벨 - 기본적인 경험치");

  private final String description;

  /**
   * 누적 경험치에 따라 적절한 경험치 레벨을 반환합니다.
   *
   * @param totalExp 누적 경험치
   * @return 해당 경험치에 맞는 ExpLevel
   */
  public static ExpLevel calculateLevel(Long totalExp) {
    if (totalExp >= 5000L) {
      return A;
    } else if (totalExp >= 2000L) {
      return B;
    } else if (totalExp >= 500L) {
      return C;
    } else {
      return D;
    }
  }
}
