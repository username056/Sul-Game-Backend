package org.sejong.sulgamewiki.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.sejong.sulgamewiki.object.DailyMemberRanking;
import org.sejong.sulgamewiki.object.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMemberRankingRepository extends JpaRepository<DailyMemberRanking, Long> {

  // 특정 회원의 최신 기록을 조회하는 메서드
  Page<DailyMemberRanking> findByMemberOrderByRecordDateDesc(Member member, Pageable pageable);

  // 특정 회원과 날짜에 해당하는 기록을 조회하는 메서드
  Optional<DailyMemberRanking> findByMemberAndRecordDate(Member member, LocalDate date);

  // 특정 날짜의 상위 100개의 랭킹을 조회하는 메서드
  Page<DailyMemberRanking> findTop100ByRecordDateOrderByRankAsc(LocalDate date, Pageable pageable);

  // 특정 회원의 모든 랭킹 기록을 삭제하는 메서드
  void deleteByMember(Member member);

  // 가장 최근의 업데이트 시간을 조회하는 메서드
  @Query("SELECT MAX(dmr.updateTime) FROM DailyMemberRanking dmr")
  Optional<LocalDateTime> findMaxUpdateTime();

  // 특정 업데이트 시간에 해당하는 랭킹을 조회하는 메서드, 정렬 및 페이징 적용
  @Query("SELECT dmr FROM DailyMemberRanking dmr WHERE dmr.updateTime = :updateTime ORDER BY dmr.rank ASC")
  Page<DailyMemberRanking> findByUpdateTime(@Param("updateTime") LocalDateTime updateTime, Pageable pageable);

  // 회원별 최신 랭킹 기록을 조회하는 메서드
  Optional<DailyMemberRanking> findTopByMemberOrderByUpdateTimeDesc(Member member);
}