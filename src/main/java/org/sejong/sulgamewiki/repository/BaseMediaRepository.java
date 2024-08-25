package org.sejong.sulgamewiki.repository;

import java.util.List;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseMediaRepository extends JpaRepository<BaseMedia, Long> {

  List<String> findMediaUrlsByBasePost_BasePostId(Long basePostId);
}
