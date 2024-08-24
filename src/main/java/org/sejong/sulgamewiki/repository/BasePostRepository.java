package org.sejong.sulgamewiki.repository;

import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.CreativeGame;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePostRepository extends JpaRepository<BasePost, Long> {

  /*--------------------------최신게시물---------------------------*/
  // 생성된 날짜를 기준으로 최신 창작게시물들을 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT b FROM CreativeGame b WHERE b.isDeleted = false ORDER BY b.createdDate DESC")
  Slice<CreativeGame> findLatestCreativeGames(Pageable pageable);

  // 생성된 날짜를 기준으로 최신 인트로 게시물들을 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT b FROM Intro b WHERE b.isDeleted = false ORDER BY b.createdDate DESC")
  Slice<Intro> findLatestIntros(Pageable pageable);



  /*--------------------------국룰술게임---------------------------*/
  // 좋아요 순으로 오피셜 게임을 정렬해서 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT o FROM OfficialGame o WHERE o.isDeleted = false ORDER BY o.likes DESC")
  Slice<OfficialGame> findOfficialGamesByLikes(Pageable pageable);




  /*--------------------------실시간 ㅅㄱㅇㅋ차트---------------------------*/




  /*--------------------------인트로 자랑하기---------------------------*/
  // 좋아요 순으로 인트로를 정렬해서 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT o FROM Intro o WHERE o.isDeleted = false ORDER BY o.likes DESC")
  Slice<Intro> findIntrosByLikes(Pageable pageable);

  // 조회수 순으로 인트로를 정렬해서 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT o FROM Intro o WHERE o.isDeleted = false ORDER BY o.views DESC")
  Slice<Intro> findByViews(Pageable pageable);



  /*--------------------------오늘 가장 핫했던 술게임---------------------------*/



}
