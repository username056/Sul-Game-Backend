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
  private String nickname;
  private Boolean isExistingNickname;

  // posts
  private List<BasePost> likedOfficialGames;
  private List<BasePost> likedCreationGames;
  private List<BasePost> likedIntros;

  private List<BasePost> bookmarkedOfficialGameIds;
  private List<BasePost> bookmarkedCreationGameIds;
  private List<BasePost> bookmarkedIntroIds;

  // reports
  private List<Report> reports;
}
