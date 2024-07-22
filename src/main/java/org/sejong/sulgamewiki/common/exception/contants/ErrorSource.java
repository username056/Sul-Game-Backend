package org.sejong.sulgamewiki.common.exception.contants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorSource {
  GAME,
  INTRO,
  NOTI,
  MEMBER,
  ADMIN,
  OTHERS;
}
