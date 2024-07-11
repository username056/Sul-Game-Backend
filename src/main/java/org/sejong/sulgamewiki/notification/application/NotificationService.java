package org.sejong.sulgamewiki.notification.application;


import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.notification.domain.entity.Notification;
import org.sejong.sulgamewiki.notification.domain.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Page<Notification> getNotificationsByMemberId(Long memberId, Pageable pageable) {
        return notificationRepository.findByMemberId(memberId, pageable);
    }
}