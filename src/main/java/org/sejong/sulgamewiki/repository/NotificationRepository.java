package org.sejong.sulgamewiki.repository;

import java.util.List;
import org.sejong.sulgamewiki.fss.FCMMessage.ApiNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<ApiNotification, Long> {
  ApiNotification findByCommentId(Long commentId);
  List<ApiNotification> findByFcmToken(String fcmToken);
}