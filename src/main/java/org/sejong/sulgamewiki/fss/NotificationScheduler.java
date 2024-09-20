package org.sejong.sulgamewiki.fss;

import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class NotificationScheduler {

  private final FCMService fcmService;

  // 매일 오전 10시에 실행
  @Scheduled(cron = "0 0 10 * * ?")
  public void sendDailyNotification() throws IOException {
    // 어제 핫했던 게임이나 인트로에 대한 데이터 가져오기
    List<String> hotContent = getHotContentForYesterday();

    String title = "어제 핫했던 콘텐츠!";
    String body = "어제 인기 있었던 게임 및 인트로를 확인하세요: " + String.join(", ", hotContent);

    // FCM 주제 구독자에게 알림 전송
    fcmService.sendMessageToTopic(FCMCommand.builder()
        .topic("daily-hot-content")
        .title(title)
        .body(body)
        .build());
  }

  // 매주 토요일 오전 10시에 실행
  @Scheduled(cron = "0 0 10 ? * SAT")
  public void sendWeeklyNotification() throws IOException {
    // 지난 주의 핫했던 게임이나 인트로에 대한 데이터 가져오기
    List<String> hotContent = getHotContentForLastWeek();

    String title = "이번 주 핫했던 콘텐츠!";
    String body = "이번 주 인기 있었던 게임 및 인트로를 확인하세요: " + String.join(", ", hotContent);

    // FCM 주제 구독자에게 알림 전송
    fcmService.sendMessageToTopic(FCMCommand.builder()
        .topic("weekly-hot-content")
        .title(title)
        .body(body)
        .build());
  }

  private List<String> getHotContentForYesterday() {
    // 어제의 인기 콘텐츠(creationGame, officialGame, intro)를 가져오는 로직 구현
    return List.of("creationGame", "officialGame", "intro");
  }

  private List<String> getHotContentForLastWeek() {
    // 지난 주의 인기 콘텐츠(creationGame, officialGame, intro)를 가져오는 로직 구현
    return List.of("creationGame", "officialGame", "intro");
  }
}
