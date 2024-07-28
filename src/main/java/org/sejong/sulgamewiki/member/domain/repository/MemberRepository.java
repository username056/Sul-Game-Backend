package org.sejong.sulgamewiki.member.domain.repository;

import java.util.Optional;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

  Optional<Member> findByEmail(String email);
}

