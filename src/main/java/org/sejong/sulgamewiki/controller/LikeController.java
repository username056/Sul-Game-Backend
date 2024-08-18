package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.util.auth.domain.CustomUserDetails;
import org.sejong.sulgamewiki.service.LikeService;
import org.sejong.sulgamewiki.util.like.dto.UpdateLikeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

  private final LikeService likeService;

  @PostMapping("/up/{basePostId}")
  @Operation(description = "좋아요 눌림")
  public ResponseEntity<UpdateLikeResponse> upLike(
      @PathVariable("basePostId") Long basePostId,
      Long memberId
  ) {
    UpdateLikeResponse response = likeService.upLike(basePostId, memberId);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/cancel/{basePostId}")
  @Operation(description = "좋아요 취소")
  public ResponseEntity<UpdateLikeResponse> downLike(
      @PathVariable Long popularGameId,
      @AuthenticationPrincipal CustomUserDetails customUserDetails
  ) {
    UpdateLikeResponse response = likeService.downLike(popularGameId, customUserDetails.getMember().getId());
    return ResponseEntity.ok(response);
  }
}
