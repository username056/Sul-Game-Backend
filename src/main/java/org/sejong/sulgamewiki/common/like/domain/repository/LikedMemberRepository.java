package org.sejong.sulgamewiki.common.like.domain.repository;

import java.util.Optional;
import org.sejong.sulgamewiki.common.entity.BasePost;
import org.sejong.sulgamewiki.common.like.domain.entity.LikedMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedMemberRepository extends JpaRepository<LikedMember, Long> {

  Optional<LikedMember> findByBasePost(BasePost basePost);
}
