package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.service.OfficialGameService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/official")
@RequiredArgsConstructor
@Tag(
    name = "국룰 게임 관리 API",
    description = "국룰 게임 관리 API 제공"
)
public class OfficialGameController implements OfficialGameControllerDocs{
  private final OfficialGameService officialGameService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<BasePostDto> createOfficialGame(
      @ModelAttribute BasePostCommand command
  ) {
    return ResponseEntity.ok(officialGameService.createOfficialGame(command));
  }

  @GetMapping("/{id}")
  @LogMonitoringInvocation
  public ResponseEntity<BasePostDto> getPopularGame(
      @ModelAttribute BasePostCommand command) {
    BasePostDto dto = officialGameService.getOfficialGame(command);
    return ResponseEntity.ok(dto);
  }
}
