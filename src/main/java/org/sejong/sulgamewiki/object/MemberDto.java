package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class MemberDto {
  private Member member;
  private MemberContentInteraction memberContentInteraction;
  private List<Member> members;
  private Long memberId;
  private String nickName;

  // posts
  List<BasePost> likedOfficialGame;
  List<BasePost> likedCreationGame;
  List<BasePost> likedIntros;

  List<BasePost> bookmarkedOfficialGameIds;
  List<BasePost> bookmarkedCreationGameIds;
  List<BasePost> bookmarkedIntroIds;

  // report
  List<Report> reports;
}
