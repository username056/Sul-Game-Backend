package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.service.IntroService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "인트로 관리 API",
    description = "인트로 관리 API 제공"
)
public class IntroController implements IntroControllerDocs{
  private final IntroService introService;

  @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<BasePostDto> createIntro(
      @ModelAttribute BasePostCommand command) {
    return ResponseEntity.ok(introService.createIntro(command));
  }

  // TODO: URL 정석 지피티한테 물어보자
  @GetMapping("/details")
  public ResponseEntity<BasePostDto> getIntro(@ModelAttribute BasePostCommand command) {
    return ResponseEntity.ok(introService.getIntro(command));
  }

  // TODO: URL 정석 지피티한테 물어보자. (이게 맞다네)
  @PutMapping(name = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> updateIntro(@ModelAttribute BasePostCommand command) {
    return ResponseEntity.ok(introService.updateIntro(command));
  }

  @DeleteMapping("/")
  public ResponseEntity<Void> deleteIntro(@ModelAttribute BasePostCommand command) {
    introService.deleteIntro(command);
    return ResponseEntity.noContent().build();
  }
}
