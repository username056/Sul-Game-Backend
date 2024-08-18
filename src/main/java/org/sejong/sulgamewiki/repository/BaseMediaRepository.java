package org.sejong.sulgamewiki.repository;

import org.sejong.sulgamewiki.object.BaseMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseMediaRepository extends JpaRepository<BaseMedia, Long> {

}
