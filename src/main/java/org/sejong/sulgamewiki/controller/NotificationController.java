package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.fss.FCMMessage.ApiNotification;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.repository.NotificationRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "알림 관리 API",
    description = "알림 관리 API 제공"
)
public class NotificationController {

  private final NotificationRepository notificationRepository;
  private final MemberRepository memberRepository;

  // 특정 회원의 알림을 조회하는 API
  @GetMapping("/member/{memberId}")
  public List<ApiNotification> getNotificationsByFcmToken(String fcmToken) {
    // FCM 토큰으로 멤버를 조회
    Member member = memberRepository.findByFcmToken(fcmToken)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 해당 멤버의 알림 조회
    return notificationRepository.findByFcmToken(member.getFcmToken());
  }
}