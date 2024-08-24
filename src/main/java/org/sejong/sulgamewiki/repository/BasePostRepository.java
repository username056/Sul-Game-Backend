package org.sejong.sulgamewiki.repository;

import java.util.List;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.OfficialGameDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePostRepository extends JpaRepository<BasePost, Long> {

  // 좋아요 순으로 오피셜 게임을 정렬해서 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT o FROM OfficialGame o ORDER BY o.likes DESC")
  Slice<OfficialGameDto> findOfficialGamesByLikes(Pageable pageable);

  // 생성된 날짜를 기준으로 최신 게시물들을 Slice로 가져오는 JPQL 쿼리
  @Query("SELECT b FROM BasePost b WHERE b.isDeleted = false ORDER BY b.createdDate DESC")
  Slice<BasePostDto> findLatestPosts(Pageable pageable);
}
