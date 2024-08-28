package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.IntroDto;
import org.sejong.sulgamewiki.service.IntroService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
@Slf4j
public class IntroController {
  private final IntroService introService;

  @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(description = "인트로 생성")
  @LogMonitoringInvocation
  public ResponseEntity<IntroDto> createIntro(
      @RequestParam("memberId") Long memberId,
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("lyrics") String lyrics,
      @RequestParam(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles  // 파일이 없어도 허용
  ) {
    BasePostCommand command = BasePostCommand.builder()
        .memberId(memberId)
        .title(title)
        .description(description)
        .lyrics(lyrics)
        .multipartFiles(multipartFiles)
        .build();

    IntroDto introDto = introService.createIntro(command);
    return ResponseEntity.ok(introDto);
  }
}
