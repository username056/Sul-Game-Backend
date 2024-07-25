package org.sejong.sulgamewiki.member.dto.request;

import java.time.LocalDate;
import lombok.Getter;


@Getter
public class CreateMemberRequest {
  private String email;
  private String nickName;
  private LocalDate birthDate;
  private String university;
  private Boolean isUniversityVisible;
  private Boolean isNotiEnabled;

}
