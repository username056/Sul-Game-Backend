package org.sejong.sulgamewiki.member.dto.response;

import lombok.Builder;
import org.sejong.sulgamewiki.member.domain.entity.Member;

@Builder
public class CreateMemberResponse {
  public String memberId;
  public String nickName;

  public static CreateMemberResponse from(Member member) {
    return CreateMemberResponse.builder()
        .memberId(builder().memberId)
        .nickName(builder().nickName)
        .build();
  }
}
