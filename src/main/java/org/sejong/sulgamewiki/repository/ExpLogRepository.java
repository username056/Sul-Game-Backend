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

  Page<ExpLog> findByMemberMemberId(Long memberId, Pageable pageable);
}

