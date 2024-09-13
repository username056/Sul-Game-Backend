package org.sejong.sulgamewiki.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.CreationGame;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.SearchCommand;
import org.sejong.sulgamewiki.object.SearchDto;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

  private final BasePostRepository basePostRepository;

  // 통합 검색 기능 제공
  public SearchDto searchAll(SearchCommand command) {
    List<BasePost> basePosts = basePostRepository.searchBasePosts(command.getQuery());

    List<Intro> intros = new ArrayList<>();
    List<OfficialGame> officialGames = new ArrayList<>();
    List<CreationGame> creationGames = new ArrayList<>();

    for (BasePost basePost : basePosts) {
      if (basePost instanceof Intro) {
        intros.add((Intro) basePost);
      } else if (basePost instanceof OfficialGame) {
        officialGames.add((OfficialGame) basePost);
      } else if (basePost instanceof CreationGame) {
        creationGames.add((CreationGame) basePost);
      }
    }

    return SearchDto.builder()
        .intros(intros)
        .officialGames(officialGames)
        .creationGames(creationGames)
        .build();
  }
}
