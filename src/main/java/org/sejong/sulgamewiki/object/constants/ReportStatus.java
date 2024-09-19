package org.sejong.sulgamewiki.object.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReportStatus {
  APPROVED("관리자 승인됨"),
  PENDING("관리자 승인 대기중");

  private final String description;
}
