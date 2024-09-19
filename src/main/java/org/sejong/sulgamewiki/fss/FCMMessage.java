package org.sejong.sulgamewiki.fss;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private Notification notification;
  }

  @Getter
  @Builder
  @Setter
  @AllArgsConstructor
  public static class Notification { // 여기에서 static 추가
    @Key("title")
    private final String title;
    @Key("body")
    private final String body;
    @Key("image")
    private final String image;
  }
}
