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
  private MemberInteraction memberInteraction;
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

  // 경험치 계산
  private Integer expRank; // 경험치 순위
  private Double expRankPercentile; // 경험치 상위 몇 퍼센트인지
  private Long nextLevelExp; // 다음 레벨까지 필요한 경험치
  private Long remainingExpForNextLevel; // 다음 레벨까지 남은 경험치
  private Double progressPercentToNextLevel; // 다음 레벨까지의 진행 퍼센트
}
