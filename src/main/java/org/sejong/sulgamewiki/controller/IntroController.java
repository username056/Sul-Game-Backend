package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.sejong.sulgamewiki.service.IntroService;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.sejong.sulgamewiki.intro.dto.response.CreateIntroResponse;
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
public class IntroController {

  private final IntroService introService;

  @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(description = "인트로 생성")
  @LogMonitoringInvocation
  public ResponseEntity<CreateIntroResponse> createIntro(

      @Parameter(description = "MemberId", required = true, example = "1")
      @RequestParam Long memberId,

      @Parameter(description = "인트로 제목", required = true, example = "인트로 제목을 적어주세요.")
      @RequestParam String title,

      @Parameter(description = "인트로 설명", required = true, example = "언제, 어떻게 부르는 인트로인지 설명해주세요.")
      @RequestParam String description,

      @Parameter(description = "노래 가사", required = true, example = "인트로 가사를 적어주세요.")
      @RequestParam String lyrics,

      @Parameter(description = "파일 목록", required = true)
      @RequestParam("multipartFiles") List<MultipartFile> files
  ) {
    // CreateIntroRequest 객체를 of 메서드 사용하여 생성
    CreateIntroRequest request = CreateIntroRequest.of(title, description, lyrics);

    // 서비스 호출하여 인트로 생성
    CreateIntroResponse introResponse = introService.createIntro(memberId, request, files);
    return ResponseEntity.ok(introResponse);
  }
}
