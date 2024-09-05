package org.sejong.sulgamewiki.repository;

import java.util.List;
import java.util.Optional;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.CreationGame;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePostRepository extends JpaRepository<BasePost, Long> {

  /*
  최신게시물
   */
  // 생성된 날짜를 기준으로 최신 창작게시물들을 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT entity FROM CreationGame entity WHERE entity.isDeleted = false ORDER BY entity.createdDate DESC")
  Slice<CreationGame> findLatestCreationGames(Pageable pageable);

  // 생성된 날짜를 기준으로 최신 인트로 게시물들을 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT entity FROM Intro entity WHERE entity.isDeleted = false ORDER BY entity.createdDate DESC")
  Slice<Intro> findLatestIntros(Pageable pageable);


  /*
  국룰술게임
   */
  // 좋아요 순으로 오피셜 게임을 정렬해서 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT entity FROM OfficialGame entity WHERE entity.isDeleted = false ORDER BY entity.likes DESC")
  Slice<OfficialGame> findOfficialGamesByLikes(Pageable pageable);


  /*
  실시간 ㅅㄱㅇㅋ차트
   */
  // 실시간 점수순으로 창작게임을 정렬해서 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT entity FROM CreationGame entity WHERE entity.isDeleted = false ORDER BY entity.dailyScore DESC")
  Slice<CreationGame> findCreationGamesByDailyScore(Pageable pageable);

  @Query("SELECT entity FROM Intro entity WHERE entity.isDeleted = false ORDER BY entity.dailyScore DESC")
  Slice<Intro> findIntrosByDailyScore(Pageable pageable);

  @Query("SELECT entity FROM OfficialGame entity WHERE entity.isDeleted = false ORDER BY entity.dailyScore DESC")
  Slice<OfficialGame> findOfficialGamesByDailyScore(Pageable pageable);


  /*
  인트로 자랑하기
   */
  // 좋아요 순으로 인트로를 정렬해서 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT entity FROM Intro entity WHERE entity.isDeleted = false ORDER BY entity.likes DESC")
  Slice<Intro> findIntrosByLikes(Pageable pageable);

  // 조회수 순으로 인트로를 정렬해서 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT entity FROM Intro entity WHERE entity.isDeleted = false ORDER BY entity.views DESC")
  Slice<Intro> findIntrosByViews(Pageable pageable);


  /*
  금주 가장 핫했던 술게임
   */
  //금주의 점수를 기준으로 술게임(창작,공식)을 정렬해서 Slice로 가져오는 JPQL 쿼리(인트로 제외)
  @Query("SELECT entity FROM BasePost entity WHERE TYPE(entity) IN (OfficialGame, CreationGame) AND entity.isDeleted = false ORDER BY entity.weeklyScore DESC")
  Slice<BasePost> findGamesByWeeklyScore(Pageable pageable);

  @Query("SELECT entity FROM BasePost entity WHERE entity.basePostId IN :ids AND entity.isDeleted = false")
  List<BasePost> findByBasePostIdIn(List<Long> ids);

  @Query("SELECT entity FROM BasePost entity WHERE entity.basePostId = :basePostId AND entity.isDeleted = false")
  Optional<BasePost> findByBasePostId(Long basePostId);

  @Query("SELECT entity FROM BaseMedia entity WHERE entity.basePost.basePostId = :basePostId AND entity.basePost.isDeleted = false")
  List<BaseMedia> findMediasByBasePostId(Long basePostId);

  @Query("SELECT entity FROM Intro entity WHERE entity.basePostId = :basePostId AND entity.isDeleted = false")
  Optional<Intro> findIntroByBasePostId(Long basePostId);

  @Query("SELECT entity FROM OfficialGame entity WHERE entity.basePostId = :basePostId AND entity.isDeleted = false")
  Optional<OfficialGame> findOfficialGameByBasePostId(Long basePostId);

  @Query("SELECT entity FROM CreationGame entity WHERE entity.basePostId = :basePostId AND entity.isDeleted = false")
  Optional<CreationGame> findCreationGameByBasePostId(Long basePostId);

}
