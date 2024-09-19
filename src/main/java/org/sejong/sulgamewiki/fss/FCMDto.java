package org.sejong.sulgamewiki.fss;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FCMDto {
  private HttpStatus successStatus;  // 성공 상태 필드 추가
  private String successContent;     // 성공 메시지 필드 추가

  private String to;  // 주제 경로, "/topics/topic-name"
  private List<String> registrationTokens;  // 구독할 디바이스 토큰 목록

  private String topic;
  private String title;
  private String body;
  private String img;
}
