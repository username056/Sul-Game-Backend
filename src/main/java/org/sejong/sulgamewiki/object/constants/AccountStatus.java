package org.sejong.sulgamewiki.object.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountStatus {
  PENDING("회원가입이 완료되지 않은 상태"),
  ACTIVE("활성화된 계정"),
  DELETED("삭제된 계정");

  private final String description;
}
