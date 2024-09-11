package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.service.IntroService;
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
@RequestMapping("/api/intro")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "인트로 관리 API",
    description = "인트로 관리 API 제공"
)
public class IntroController implements IntroControllerDocs{
  private final IntroService introService;
  private final LikeService likeService;

  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<BasePostDto> createIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    return ResponseEntity.ok(introService.createIntro(command));
  }

  @PostMapping(value = "/get", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> getIntro(@ModelAttribute BasePostCommand command) {
    return ResponseEntity.ok(introService.getIntro(command));
  }

  @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> updateIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    return ResponseEntity.ok(introService.updateIntro(command));
  }

  @PostMapping(value = "/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> deleteIntro(@AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    introService.deleteIntro(command);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/like", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BasePostDto> likeIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    if (!command.getIsLiked()){
      return ResponseEntity.ok(likeService.upPostLike(command));
    } else {
      return ResponseEntity.ok(likeService.cancelPostLike(command));
    }
  }
}
