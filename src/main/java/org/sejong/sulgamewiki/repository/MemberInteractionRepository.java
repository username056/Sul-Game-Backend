package org.sejong.sulgamewiki.repository;

import java.util.Optional;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInteractionRepository extends JpaRepository<MemberInteraction, Long> {
  // 전체 회원 수 조회
  @Query("SELECT COUNT(mi) FROM MemberInteraction mi")
  long countAllMembers();

  // 특정 회원보다 경험치가 높은 회원 수 조회 (이 회원의 순위를 계산하기 위함)
  @Query("SELECT COUNT(mi) FROM MemberInteraction mi WHERE mi.exp > :exp")
  long countMembersWithMoreExpThan(@Param("exp") Long exp);
}
