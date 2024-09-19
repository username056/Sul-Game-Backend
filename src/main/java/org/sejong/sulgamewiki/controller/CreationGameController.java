package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.service.BookmarkService;
import org.sejong.sulgamewiki.service.CreationGameService;
import org.sejong.sulgamewiki.service.LikeService;
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
@RequestMapping("/api/creation")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "창작 게임 관리 API",
    description = "창작 게임 관리 API 제공"
)
public class CreationGameController {

  private final CreationGameService creationGameService;
  private final LikeService likeService;
  private final BookmarkService bookmarkService;

  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<BasePostDto> createCreationGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    return ResponseEntity.ok(creationGameService.createCreationGame(command));
  }

  @PostMapping(value = "/details", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<BasePostDto> getCreationGame(
      @ModelAttribute BasePostCommand command) {
    return ResponseEntity.ok(creationGameService.getCreationGame(command));
  }

  @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> updateCreationGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    return ResponseEntity.ok(creationGameService.updateCreationGame(command));
  }

  @PostMapping(value = "/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> deleteCreationGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    creationGameService.deleteCreationGame(command);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/like", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> likeCreation(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    return ResponseEntity.ok(likeService.likePost(command));
  }

  @PostMapping(value = "/bookmark", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> bookmarkCreation(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command
  ){
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    return ResponseEntity.ok(bookmarkService.bookmarkPost(command));
  }
}
