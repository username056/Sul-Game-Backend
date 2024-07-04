package org.sejong.sulgamewiki.game.popular.presentation;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.sejong.sulgamewiki.game.popular.application.PopularGameService;
import org.sejong.sulgamewiki.game.popular.dto.PopularGameDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/popular-game")
@RequiredArgsConstructor
public class PopularGameController {

  private final PopularGameService popularGameService;


  // localhost:8080/api/popular-game?gameId=1
  //
  //      @PathVariable("gameId") String gameId,
  @GetMapping("")
  public ResponseEntity<PopularGameDto> getPopularGame(
      @RequestParam("gameId") Long gameId
  ){
    // Service 단에서 getPopularGame 로직 실행
    PopularGameDto popularGameDto = popularGameService.getPopularGame(gameId);

    return ResponseEntity.ok().body(popularGameDto);
  }

}
