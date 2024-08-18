package org.sejong.sulgamewiki.repository;

import org.sejong.sulgamewiki.object.BasePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePostRepository extends JpaRepository<BasePost, Long> {
}
