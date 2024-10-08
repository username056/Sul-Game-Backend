package org.sejong.sulgamewiki.object;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.constants.ExpLevel;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Slf4j
public class MemberInteraction extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private Member member;

  // 현재 경험치
  @Builder.Default
  @Column(nullable = false)
  private Long exp = 0L;

  // 현재 경험치 레벨
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private ExpLevel expLevel = ExpLevel.D;

  // 매일 업데이트되는 순위 정보
  @Builder.Default
  @Column(nullable = false)
  private Integer currentRank = 0;

  // 순위 변동 정보 (매일 자정에 업데이트)
  @Builder.Default
  @Column(nullable = false)
  private Integer previousRank = 0;

  // 하루 방문 횟수를 기록하는 필드
  @Builder.Default
  @Column(nullable = false)
  private Integer dailyVisitCount = 0;

  // 마지막 방문한 날짜
  // 주의: 처음 생성시 null 로 생성, VisitCountFilter 에서 업데이트
  private LocalDate lastVisitDate;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalLikeCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalCommentCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalPostCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalCommentLikeCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalPostLikeCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalMediaCount = 0;

  @Builder.Default
  @ElementCollection
  private List<Long> likedOfficialGameIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> likedCreationGameIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> likedIntroIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> bookmarkedOfficialGameIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> bookmarkedCreationGameIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> bookmarkedIntroIds = new ArrayList<>();

  /**
   * 방문 횟수를 1 증가시키는 메서드 당일 첫 방문일 경우에 호출된다.
   */
  public void upDailyVisitCount() {
    this.dailyVisitCount++;
    this.lastVisitDate = LocalDate.now();
    log.info("회원 활동 ID : {} : 방문 날짜 : {}", id, lastVisitDate);
    log.info("회원 활동 ID : {} : 방문 횟수 증가 +1 : 총 {}", id, dailyVisitCount);
  }

  // 경험치 추가
  public void addExp(Long points) {
    this.exp += points;
  }

  public void increaseCommentCount(){this.totalCommentCount++;}

  public void decreaseCommentCount(){this.totalCommentCount--;}

  public void increaseCommentLikeCount(){this.totalLikeCount++;}

  public void decreaseCommentLikeCount(){this.totalLikeCount--;}

  public void addLikedPostId(BasePostCommand command){
    if(command.getBasePost().getSourceType().equals(SourceType.INTRO)){
      this.likedIntroIds.add(command.getBasePostId());
    } else if (command.getBasePost().getSourceType().equals(SourceType.OFFICIAL_GAME)) {
      this.likedOfficialGameIds.add(command.getBasePostId());
    } else if (command.getBasePost().getSourceType().equals(SourceType.CREATION_GAME)) {
      this.likedCreationGameIds.add(command.getBasePostId());
    }else{
      throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }
  }

  public void removeLikedPostId(BasePostCommand command){
    if(command.getBasePost().getSourceType().equals(SourceType.INTRO)){
      this.likedIntroIds.remove(command.getBasePostId());
    } else if (command.getBasePost().getSourceType().equals(SourceType.OFFICIAL_GAME)) {
      this.likedOfficialGameIds.remove(command.getBasePostId());
    } else if (command.getBasePost().getSourceType().equals(SourceType.CREATION_GAME)) {
      this.likedCreationGameIds.remove(command.getBasePostId());
    } else{
      throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }
  }

  public void addBookmarkedPostId(BasePostCommand command) {
    if(command.getBasePost().getSourceType().equals(SourceType.INTRO)){
      this.bookmarkedIntroIds.add(command.getBasePostId());
    } else if (command.getBasePost().getSourceType().equals(SourceType.OFFICIAL_GAME)) {
      this.bookmarkedOfficialGameIds.add(command.getBasePostId());
    } else if (command.getBasePost().getSourceType().equals(SourceType.CREATION_GAME)) {
      this.bookmarkedCreationGameIds.add(command.getBasePostId());
    }else{
      throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }
  }

  public void removeBookmarkedPostId(BasePostCommand command) {
    if(command.getBasePost().getSourceType().equals(SourceType.INTRO)){
      this.bookmarkedIntroIds.remove(command.getBasePostId());
    } else if (command.getBasePost().getSourceType().equals(SourceType.OFFICIAL_GAME)) {
      this.bookmarkedOfficialGameIds.remove(command.getBasePostId());
    } else if (command.getBasePost().getSourceType().equals(SourceType.CREATION_GAME)) {
      this.bookmarkedCreationGameIds.remove(command.getBasePostId());
    } else{
      throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }
  }
}