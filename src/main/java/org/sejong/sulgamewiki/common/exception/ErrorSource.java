package org.sejong.sulgamewiki.common.exception;

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
