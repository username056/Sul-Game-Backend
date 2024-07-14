package org.sejong.sulgamewiki.game.popular.presentation;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.game.popular.application.PopularGameService;
import org.sejong.sulgamewiki.game.popular.dto.request.CreatePopularGameRequest;
import org.sejong.sulgamewiki.game.popular.dto.response.CreatePopularGameResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/popular-game")
@RequiredArgsConstructor
public class PopularGameController {

  private final PopularGameService popularGameService;

  @PostMapping("/")
  public ResponseEntity<CreatePopularGameResponse> createPopularGame(
      @RequestParam Long memberId,
      @RequestBody CreatePopularGameRequest request) {
    CreatePopularGameResponse popularGame
        = popularGameService.createPopularGame(memberId, request);
    return ResponseEntity.ok(popularGame);
  }
}
