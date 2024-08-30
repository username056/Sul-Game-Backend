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

  @GetMapping("/{basePostId}")
  @Operation(summary = "인트로 조회", description = "특정 인트로를 조회합니다.")
  public ResponseEntity<BasePostDto> getIntro(@PathVariable Long basePostId) {
    BasePostDto intro = introService.getIntro(basePostId);
    return ResponseEntity.ok(intro);
  }

  @GetMapping
  @Operation(summary = "인트로 전체 조회", description = "모든 인트로를 조회합니다.")
  public ResponseEntity<List<BasePostDto>> getAllIntros() {
    List<BasePostDto> intros = introService.getAllIntros();
    return ResponseEntity.ok(intros);
  }

  @PutMapping(value = "/{basePostId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "인트로 수정", description = "기존 인트로를 수정합니다.")
  public ResponseEntity<BasePostDto> updateIntro(@ModelAttribute BasePostCommand command) {
    BasePostDto updatedIntro = introService.updateIntro(command);
    return ResponseEntity.ok(updatedIntro);
  }

  @DeleteMapping("/{basePostId}")
  @Operation(summary = "인트로 삭제", description = "특정 인트로를 소프트 삭제합니다.")
  public ResponseEntity<Void> deleteIntro(@PathVariable Long basePostId) {
    introService.deleteIntro(basePostId);
    return ResponseEntity.noContent().build();
  }
}
