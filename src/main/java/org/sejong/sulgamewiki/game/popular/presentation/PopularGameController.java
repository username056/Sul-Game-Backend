package org.sejong.sulgamewiki.game.popular.presentation;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.docs.PopularGameControllerDocs;
import org.sejong.sulgamewiki.common.utils.annotations.FilesParameter;
import org.sejong.sulgamewiki.game.popular.application.PopularGameService;
import org.sejong.sulgamewiki.game.popular.dto.request.CreatePopularGameRequest;
import org.sejong.sulgamewiki.game.popular.dto.response.CreatePopularGameResponse;
import org.sejong.sulgamewiki.game.popular.dto.response.GetPopularGameResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/popular-game")
@RequiredArgsConstructor
public class PopularGameController implements PopularGameControllerDocs {

  private final PopularGameService popularGameService;

  @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<CreatePopularGameResponse> createPopularGame(
      @RequestParam Long memberId,
      @FilesParameter CreatePopularGameRequest request
  ) {
    CreatePopularGameResponse popularGame
        = popularGameService.createPopularGame(memberId, request);
    return ResponseEntity.ok(popularGame);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetPopularGameResponse> getPopularGame(@PathVariable Long id) {
    GetPopularGameResponse response = popularGameService.getPopularGame(id);
    return ResponseEntity.ok(response);
  }
}
