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
public class TestDto {
  //mockMember
  private Member member;
  private MemberContentInteraction memberContentInteraction;

  // 삭제된 객체
  private Long deletedMemberId;
  private Long deletedMemberContentInteractionId;

  private String nickname;
  private String email;
  private LocalDate birthDate;
  private List<Member> members;
  private List<MemberContentInteraction> memberContentInteractions;

  //mock Post
  private List<BasePost> basePosts;
}
