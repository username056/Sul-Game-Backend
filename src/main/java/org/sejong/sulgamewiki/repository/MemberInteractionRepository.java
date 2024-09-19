package org.sejong.sulgamewiki.repository;

import java.util.List;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.springframework.data.domain.Pageable;
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

  // 경험치 내림차순으로 상위 50명의 회원을 조회하는 메서드
  @Query("SELECT mi FROM MemberInteraction mi ORDER BY mi.exp DESC")
  List<MemberInteraction> findTop50ByOrderByExpDesc();

  // 페이징을 적용하여 회원을 경험치 순으로 조회하는 메서드
  @Query("SELECT mi.member FROM MemberInteraction mi")
  List<Member> findMemberOrderByMemberInteractionPageable(Pageable pageable);

  // 특정 회원을 삭제하는 메서드
  void deleteByMember(Member member);
}