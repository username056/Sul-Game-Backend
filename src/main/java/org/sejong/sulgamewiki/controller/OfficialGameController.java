package org.sejong.sulgamewiki.controller;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.OfficialGameCommand;
import org.sejong.sulgamewiki.object.OfficialGameDto;
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
@RequestMapping("/api/popular-game")
@RequiredArgsConstructor
public class OfficialGameController {

  private final OfficialGameService officialGameService;

  @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<OfficialGameDto> createOfficialGame(
      @ModelAttribute OfficialGameCommand officialGameCommand
  ) {
    OfficialGameDto officialGameDto
        = officialGameService.createOfficialGame(officialGameCommand);
    return ResponseEntity.ok(officialGameDto);
  }

  @GetMapping("/{id}")
  @LogMonitoringInvocation
  public ResponseEntity<OfficialGameDto> getPopularGame(
      @ModelAttribute OfficialGameCommand officialGameCommand) {
    OfficialGameDto OfficialGameDto = officialGameService.getOfficialGame(officialGameCommand);
    return ResponseEntity.ok(OfficialGameDto);
  }
}
