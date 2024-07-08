package org.sejong.sulgamewiki.notification.application;

import org.sejong.sulgamewiki.notification.domain.entity.Notification;
import org.sejong.sulgamewiki.notification.domain.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}