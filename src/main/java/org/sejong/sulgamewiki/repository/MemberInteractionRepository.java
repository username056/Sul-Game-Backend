package org.sejong.sulgamewiki.repository;

import java.util.Optional;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInteractionRepository extends JpaRepository<MemberInteraction, Long> {

  Optional<MemberInteraction> findByMember(Member member);
}
