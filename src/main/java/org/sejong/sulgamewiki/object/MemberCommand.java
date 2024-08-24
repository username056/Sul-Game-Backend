package org.sejong.sulgamewiki.object;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class MemberCommand {
  private Long memberId;
  private String memberNickName;
  private String nickName;
  private LocalDate birthDate;
  private String university;
  private Boolean isUniversityVisible;
  private Boolean isNotiEnabled;
}
