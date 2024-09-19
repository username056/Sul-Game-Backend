package org.sejong.sulgamewiki.fss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {


  private final String API_URL = "https://fcm.googleapis.com/v1/projects/sul-game/messages:send";
  // 주제 구독을 위한 URL 추가
  private final String SUBSCRIBE_URL = "https://iid.googleapis.com/iid/v1:batchAdd";

  private final ObjectMapper objectMapper;
  private final MemberRepository memberRepository;

  // 주제 구독
  public void subscribeToTopic(FCMCommand command) {
    try {
      String message = makeSubscriptionMessage(command);
      sendSubscriptionRequest(message);
    } catch (IOException e) {
      log.error("FCM 주제 구독 중 오류 발생: {}", e.getMessage());
      throw new CustomException(ErrorCode.FCM_SUBSCRIBE_ERROR);
    }
  }

  // 구독 메시지 생성
  private String makeSubscriptionMessage(FCMCommand command) throws JsonProcessingException {
    // FCM IID API에서 요구하는 형식으로 구독 요청 생성
    FCMDto fcmDto = FCMDto.builder()
        .to("/topics/" + command.getTopic())  // 주제 경로
        .registrationTokens(List.of(command.getToken()))  // 구독할 디바이스 토큰 목록
        .build();

    return objectMapper.writeValueAsString(fcmDto);
  }

  // 구독 요청을 FCM에 전송
  private void sendSubscriptionRequest(String message) throws IOException {
    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

    Request request = new Request.Builder()
        .url(SUBSCRIBE_URL)
        .post(requestBody)
        .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
        .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        log.error("FCM 주제 구독 실패: {}", response.body().string());
        throw new CustomException(ErrorCode.FCM_SUBSCRIBE_ERROR);
      }
      log.info("FCM 주제 구독 성공: {}", response.body().string());
    }
  }


  ///-----////-----///-----
  // 메시지를 구성하고 FCM을 통해 단일 기기 또는 토픽에 메시지를 전송
  public void sendMessageTo(FCMCommand command) {
    try {
      // NotificationCommand 객체를 그대로 makeMessage에 넘겨줌
      String message = makeMessage(command);
      sendFcmRequest(message);
    } catch (IOException e) {
      log.error("FCM 메시지 전송 중 오류 발생: {}", e.getMessage());
      throw new CustomException(ErrorCode.FCM_SEND_ERROR);
    }
  }


  public void sendMessageToTopic(FCMCommand command) {
    try {
      String message = makeMessage(command);
      sendFcmRequest(message);
    } catch (IOException e) {
      log.error("FCM 토픽 메시지 전송 중 오류 발생: {}", e.getMessage());
      throw new CustomException(ErrorCode.FCM_SEND_ERROR);
    }
  }

  // 메시지를 구성한다 (단일 토큰 또는 토픽으로)
  private String makeMessage(FCMCommand command)
      throws JsonProcessingException {
    FCMMessage.ApiNotification notification = FCMMessage.ApiNotification.builder()
        .title(command.getTitle())
        .body(command.getBody())  // 여기는 'body'를 사용해야 합니다.
        .image(command.getImage())
        .build();

    FCMMessage.Message.MessageBuilder messageBuilder = FCMMessage.Message.builder()
        .apiNotification(notification);

    if (command.getToken() != null) {
      messageBuilder.token(command.getToken());
    } else if (command.getTopic() != null) {
      messageBuilder.topic(command.getTopic());
    } else {
      throw new CustomException(ErrorCode.INVALID_REQUEST);
    }

    FCMMessage fcmMessage = FCMMessage.builder()
        .message(messageBuilder.build())
        .validateOnly(false)
        .build();

    return objectMapper.writeValueAsString(fcmMessage);
  }

  // FCM 서버에 메시지를 전송
  private void sendFcmRequest(String message) throws IOException {
    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = RequestBody.create(message,
        MediaType.get("application/json; charset=utf-8"));

    Request request = new Request.Builder()
        .url(API_URL)
        .post(requestBody)
        .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
        .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        log.error("FCM 전송 실패: {}", response.body().string());
        throw new CustomException(ErrorCode.FCM_SEND_ERROR);
      }
      log.info("FCM 메시지 전송 성공: {}", response.body().string());
    }
  }

  // Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급
  private String getAccessToken() throws IOException {
    final String firebaseConfigPath = "firebase/sul-game-firebase-adminsdk.json";

    try {
      final GoogleCredentials googleCredentials = GoogleCredentials
          .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
          .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

      googleCredentials.refreshIfExpired();
      log.info("Access Token 발급 성공: {}", googleCredentials.getAccessToken());
      return googleCredentials.getAccessToken().getTokenValue();

    } catch (IOException e) {
      log.error("Google Access Token 요청 중 오류 발생: {}", e.getMessage());
      throw new CustomException(ErrorCode.GOOGLE_REQUEST_TOKEN_ERROR);
    }
  }

  // 토큰 기반 메시지 전송
  public ResponseEntity<String> sendByToken(FCMCommand command) {
    try {
      String message = makeMessage(command);
      sendFcmRequest(message);
      return ResponseEntity.ok("Message sent successfully");
    } catch (IOException e) {
      log.error("FCM 전송 실패: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to send message: " + e.getMessage());
    }
  }

  // 토픽 기반 메시지 전송
  public ResponseEntity<String> sendByTopic(FCMCommand command) {
    try {
      String message = makeMessage(command);
      sendFcmRequest(message);
      return ResponseEntity.ok("Message sent to topic successfully");
    } catch (IOException e) {
      log.error("FCM 전송 실패: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to send message: " + e.getMessage());
    }
  }

  // 멤버의 FCM 토큰 업데이트
  public void updateMemberFcmToken(Long memberId, String fcmToken) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    member.updateFcmToken(fcmToken);
    memberRepository.save(member);
  }
}
