package org.sejong.sulgamewiki.fss;

import com.google.api.client.util.Key;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
public class FCMMessage {
  private Message message;
  private boolean validateOnly;

  @Getter
  @Builder
  public static class Message {
    private String token;
    private String topic;
    private ApiNotification apiNotification;
  }

  @Getter
  @Builder
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor(force = true)
  @Entity
  @Table(name = "api_notifications") // 데이터베이스 테이블 이름 지정 (필요 시 변경)
  public static class ApiNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message; // 알림 메시지 내용

    @Column(nullable = false)
    private boolean isRead; // 읽었는지 여부

    @Column(nullable = true)
    private Long commentId; // 관련된 댓글 ID (필요 시)

    @Key("title")
    @Column(nullable = false)
    private final String title;

    @Key("body")
    @Column(nullable = false)
    private final String body;

    @Key("image")
    @Column(nullable = true)
    private final String image;

    @Column(nullable = true)
    private String fcmToken;

    // Custom Constructor for ApiNotification
    public ApiNotification(String title, String body, Long commentId, String image) {
      this.title = title;
      this.body = body;
      this.commentId = commentId;
      this.isRead = false; // 기본값으로 읽지 않은 상태
      this.image = image;
      this.message = body; // 기본 메시지를 body로 설정
      this.fcmToken = fcmToken;
    }
  }

}
