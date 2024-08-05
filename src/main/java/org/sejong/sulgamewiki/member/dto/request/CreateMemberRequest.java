package org.sejong.sulgamewiki.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class CreateMemberRequest {
  private Long memberId;
  private String nickName;
  private LocalDate birthDate;
  private String university;
  private Boolean isUniversityVisible;
  private Boolean isNotiEnabled;
}
