package org.sejong.sulgamewiki.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.sejong.sulgamewiki.member.domain.entity.Member;

@ToString
@Getter
@Builder
public class CreateMemberResponse {
  public Long memberId;
  public String nickName;

  public static CreateMemberResponse from(Member member) {
    return CreateMemberResponse.builder()
        .memberId(member.getId())
        .nickName(member.getNickNameFromBaseMember())
        .build();
  }
}
