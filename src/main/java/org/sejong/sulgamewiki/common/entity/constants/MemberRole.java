package org.sejong.sulgamewiki.common.entity.constants;

import lombok.Getter;

@Getter
public enum MemberRole {
  ROLE_ADMIN("관리자 계정"),
  ROLE_USER("일반 회원");

  private final String description;

  MemberRole(String description){
    this.description = description;
  }
}
