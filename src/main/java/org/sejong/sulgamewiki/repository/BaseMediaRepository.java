package org.sejong.sulgamewiki.repository;

import java.util.List;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseMediaRepository extends JpaRepository<BaseMedia, Long> {

  @Query("SELECT entity.mediaUrl FROM BaseMedia entity WHERE entity.basePost.basePostId = :basePostId AND entity.isDeleted = false")
  List<String> findMediaUrlsByBasePostId(Long basePostId);

  @Query("SELECT entity FROM BaseMedia entity WHERE entity.basePost.basePostId = :basePostId AND entity.isDeleted = false")
  List<BaseMedia> findAllByBasePost_BasePostId(Long basePostId);

  @Query("SELECT entity FROM BaseMedia entity WHERE entity.mediaUrl = :mediaUrl AND entity.isDeleted = false")
  BaseMedia findByMediaUrl(String mediaUrl);

  @Query("SELECT entity FROM BaseMedia entity WHERE entity.basePost.basePostId = :basePostId AND entity.isDeleted = false")
  List<BaseMedia> findByBasePost_BasePostId(Long basePostId);

  void deleteByBasePost_BasePostId(Long basePostId);
}
