package org.sejong.sulgamewiki.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/official")
@RequiredArgsConstructor
public class OfficialGameController {
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
  public ResponseEntity<OfficialGameDto> getPopularGame(
      @ModelAttribute OfficialGameCommand officialGameCommand) {
    OfficialGameDto OfficialGameDto = officialGameService.getOfficialGame(officialGameCommand);
    return ResponseEntity.ok(OfficialGameDto);
  }
}
