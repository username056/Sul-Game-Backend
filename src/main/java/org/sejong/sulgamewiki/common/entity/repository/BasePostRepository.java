package org.sejong.sulgamewiki.common.entity.repository;

import org.sejong.sulgamewiki.common.entity.BasePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePostRepository extends JpaRepository<BasePost, Long> {

}
