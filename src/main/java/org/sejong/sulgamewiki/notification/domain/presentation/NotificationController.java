package org.sejong.sulgamewiki.notification.domain.presentation;

import org.sejong.sulgamewiki.notification.application.NotificationService;
import org.sejong.sulgamewiki.notification.domain.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public List<Notification> getNotifications() {
        return notificationService.getAllNotifications();
    }
}