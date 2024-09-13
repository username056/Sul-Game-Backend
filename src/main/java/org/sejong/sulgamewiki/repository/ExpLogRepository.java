package org.sejong.sulgamewiki.repository;

import java.util.List;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.ExpLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpLogRepository extends JpaRepository<ExpLog, Long> {

  List<ExpLog> findByMember(Member member);

  @Query("select count(*) from comment where isDeleted = false AND basePost = OfficialGame AND entity.basePostId = :basePostId ORDERBY cou limit :limit AND offset: offSet")
  Page<ExpLog> findByMemberMemberId(Long memberId, Pageable pageable);
}

