package org.sejong.sulgamewiki.object;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class MockDto {
  //mockMember
  private Member member;
  private MemberContentInteraction memberContentInteraction;

  private String nickname;
  private String email;
  private LocalDate birthDate;
  private List<Member> members;
  private List<MemberContentInteraction> memberContentInteractions;
}
