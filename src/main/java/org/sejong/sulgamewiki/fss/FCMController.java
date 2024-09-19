package org.sejong.sulgamewiki.fss;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.CustomUserDetails;
import org.sejong.sulgamewiki.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FCMController {

  private final FCMService fcmService;
  private final MemberService memberService;

  @PostMapping("/notification/token")
  public ResponseEntity<String> sendMessageToken(@RequestBody FCMCommand command) {
    return fcmService.sendByToken(command);
  }

  @PostMapping("/notification/topic")
  public ResponseEntity<String> sendMessageTopic(@RequestBody FCMCommand command) {
    return fcmService.sendByTopic(command);
  }

  @PostMapping("/update-token")
  public ResponseEntity<Void> updateFcmToken(
      @RequestBody String fcmToken,
      @ModelAttribute FCMCommand command,
      @AuthenticationPrincipal UserDetails userDetails) {

    // 로그인한 사용자 정보에서 memberId를 가져와 FCM 토큰을 업데이트
    Long memberId = command.getMember().getMemberId();
    memberService.updateFcmTokenAfterLogin(memberId, fcmToken);

    return ResponseEntity.ok().build();
  }
}
