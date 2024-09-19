package org.sejong.sulgamewiki.fss;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/topic")
@RequiredArgsConstructor
public class TopicController {

  private final FCMService fcmService;

  @PostMapping("/subscribe")
  public ResponseEntity<FCMDto> subscribrToTopic(@RequestBody FCMCommand command) {
    fcmService.subscribeToTopic(command);
    return ResponseEntity.ok().body(FCMDto.builder()
        .successStatus(HttpStatus.OK)
        .successContent(command.getTopic() +"토픽을 구독합니다.")
        .build()
    );
  }

}
