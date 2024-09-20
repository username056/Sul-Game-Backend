package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

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

  private List<CreationGame> myCreationGames;
  private List<Intro> myIntros;

  // reports
  private List<Report> reports;

  // 경험치 계산
  private Long exp; // 현재 경험치
  private Integer expRank; // 경험치 순위
  private Double expRankPercentile; // 경험치 상위 몇 퍼센트인지
  private Long nextLevelExp; // 다음 레벨까지 필요한 경험치
  private Long remainingExpForNextLevel; // 다음 레벨까지 남은 경험치
  private Double progressPercentToNextLevel; // 다음 레벨까지의 진행 퍼센트
  private Integer rankChange; // 랭킹 변동 (기준: 어제)

  // 페이징 관련 변수
  private List<ExpLog> expLogs;  // 경험치 변동 내역 리스트
  private int totalPages;        // 총 페이지 수
  private int currentPage;       // 현재 페이지 번호
  private long totalElements;    // 총 로그 수

  // 회원 랭킹
  private Page<DailyMemberRanking> rankingHistoryPage;

  // 페이징 정보를 위한 필드
  private int pageNumber; // 현재 페이지 번호
  private int pageSize;   // 페이지 크기
}
