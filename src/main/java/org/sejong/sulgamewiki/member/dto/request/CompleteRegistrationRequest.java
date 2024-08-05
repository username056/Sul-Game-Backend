package org.sejong.sulgamewiki.member.dto.request;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class CompleteRegistrationRequest {
  private Long memberId;
  private String nickName;
  private LocalDate birthDate;
  private String university;
  private Boolean isUniversityVisible;
  private Boolean isNotiEnabled;
}
