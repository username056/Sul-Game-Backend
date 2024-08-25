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
  // member
  private Member member;
  private MemberContentInteraction memberContentInteraction;
  private List<Member> members;
  private Long memberId;
  private String nickName;

  // posts
  List<BasePost> likedOfficialGames;
  List<BasePost> likedCreationGames;
  List<BasePost> likedIntros;

  List<BasePost> bookmarkedOfficialGameIds;
  List<BasePost> bookmarkedCreationGameIds;
  List<BasePost> bookmarkedIntroIds;

  // reports
  List<Report> reports;
}
