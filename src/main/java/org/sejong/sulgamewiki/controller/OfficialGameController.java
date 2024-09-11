package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
import org.sejong.sulgamewiki.service.LikeService;
import org.sejong.sulgamewiki.service.OfficialGameService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/official")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "공식 게임 관리 API",
    description = "공식 게임 관리 API 제공"
)
public class OfficialGameController implements OfficialGameControllerDocs {
  private final OfficialGameService officialGameService;
  private final LikeService likeService;

  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<BasePostDto> createOfficialGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    return ResponseEntity.ok(officialGameService.createOfficialGame(command));
  }

  @PostMapping("/details")
  @LogMonitoringInvocation
  public ResponseEntity<BasePostDto> getOfficialGame(
      @ModelAttribute BasePostCommand command) {
    return ResponseEntity.ok(officialGameService.getOfficialGame(command));
  }

  @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> updateOfficialGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    return ResponseEntity.ok(officialGameService.updateOfficialGame(command));
  }

  @PostMapping(value = "/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> deleteOfficialGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    officialGameService.deleteOfficialGame(command);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/like", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> likeOfficial(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    return ResponseEntity.ok(likeService.postLike(command));
  }
}
