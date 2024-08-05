package org.sejong.sulgamewiki.notification.presentation;

import org.sejong.sulgamewiki.common.log.LogMonitoringInvocation;
import org.sejong.sulgamewiki.notification.application.NotificationService;
import org.sejong.sulgamewiki.notification.domain.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/members/{memberId}/notifications")
    @LogMonitoringInvocation
    public Page<Notification> getNotificationsByMemberId(@PathVariable Long memberId, Pageable pageable) {
        return notificationService.getNotificationsByMemberId(memberId, pageable);
    }
}