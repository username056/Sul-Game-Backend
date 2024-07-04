package org.sejong.sulgamewiki.game.popular.application;

import static org.sejong.sulgamewiki.game.exception.GameErrorCode.*;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.game.exception.GameException;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;
import org.sejong.sulgamewiki.game.popular.domain.repository.PopularGameRepository;
import org.sejong.sulgamewiki.game.popular.dto.PopularGameDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularGameService {
  private final PopularGameRepository popularGameRepository;

  public PopularGameDto getPopularGame(long popularGameId) {
    // Repository 단에서 Entity 가져오기
    PopularGame popularGame = popularGameRepository.findById(popularGameId)
        .orElseThrow(() -> new GameException(GAME_NOT_FOUND));

    // DTO로 변환시키기 ( Custom Data )
    PopularGameDto popularGameDto = PopularGameDto.fromEntity(popularGame);

    return popularGameDto;
  }
}
