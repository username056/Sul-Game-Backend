package org.sejong.sulgamewiki.repository;

import java.util.Optional;
import org.sejong.sulgamewiki.util.entity.BasePost;
import org.sejong.sulgamewiki.util.like.domain.entity.LikedMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedMemberRepository extends JpaRepository<LikedMember, Long> {

  Optional<LikedMember> findByBasePost(BasePost basePost);
}
