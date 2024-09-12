package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.CommentCommand;
import org.sejong.sulgamewiki.object.CommentDto;
import org.sejong.sulgamewiki.service.CommentService;
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
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(
    name = "댓글 관리 API",
    description = "댓글 관리 API 제공"
)
public class CommentController implements CommentControllerDocs{

  private final CommentService commentService;
  private final LikeService likeService;

  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<CommentDto> createComment(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute CommentCommand command) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    return ResponseEntity.ok(commentService.createComment(command));
  }

  @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<CommentDto> updateComment(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute CommentCommand command){
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    return ResponseEntity.ok(commentService.updateComment(command));
  }

  @PostMapping(value = "/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<Void> deleteComment(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute CommentCommand command
  ) {
    command.setMemberId(Long.parseLong(userDetails.getUsername()));
    commentService.deleteComment(command);

    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/like", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<CommentDto> commentLike(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute CommentCommand command
  ){
    command.setMemberId(Long.parseLong(userDetails.getUsername()));

    return ResponseEntity.ok(likeService.likeComment(command));
  }
}
