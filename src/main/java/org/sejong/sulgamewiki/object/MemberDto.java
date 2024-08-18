package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberDto {
  private Member member;
  private MemberContentInteraction memberContentInteraction;
  private List<Member> members;
  private Long memberId;
  private String nickName;

}
