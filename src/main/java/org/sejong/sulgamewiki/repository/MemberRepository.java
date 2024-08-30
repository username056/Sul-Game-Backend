package org.sejong.sulgamewiki.repository;

import java.util.List;
import java.util.Optional;
import org.sejong.sulgamewiki.object.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

  Optional<Member> findByEmail(String email);

  boolean existsByNickname(String nickname);

  @Query("SELECT m.memberId FROM Member m")
  List<Long> findAllMemberIds();
}

