package org.sejong.sulgamewiki.repository;

import org.sejong.sulgamewiki.util.entity.BaseMedia;
import org.sejong.sulgamewiki.util.entity.BasePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseMediaRepository extends JpaRepository<BaseMedia, Long> {

}
