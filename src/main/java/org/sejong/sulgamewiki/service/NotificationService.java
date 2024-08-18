//package org.sejong.sulgamewiki.service;
//
//
//import lombok.RequiredArgsConstructor;
//import org.sejong.sulgamewiki.object.Notification;
//import org.sejong.sulgamewiki.repository.NotificationRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class NotificationService {
//
//    private final NotificationRepository notificationRepository;
//
//    public Page<Notification> getNotificationsByMemberId(Long memberId, Pageable pageable) {
//        return notificationRepository.findByMemberId(memberId, pageable);
//    }
//}