package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.IntroCommand;
import org.sejong.sulgamewiki.object.IntroDto;
import org.sejong.sulgamewiki.service.IntroService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
public class IntroController {

  private final IntroService introService;

  @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(description = "인트로 생성")
  @LogMonitoringInvocation
  public ResponseEntity<IntroDto> createIntro(
      @ModelAttribute IntroCommand command
  ) {
    IntroDto IntroDto = introService.createIntro(command);
    return ResponseEntity.ok(IntroDto);
  }
}
