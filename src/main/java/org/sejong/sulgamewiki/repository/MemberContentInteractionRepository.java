package org.sejong.sulgamewiki.repository;

import org.sejong.sulgamewiki.object.MemberContentInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberContentInteractionRepository extends JpaRepository<MemberContentInteraction, Long> {
}
