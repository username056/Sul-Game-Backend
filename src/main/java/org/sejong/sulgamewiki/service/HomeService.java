package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.CreationGame;
import org.sejong.sulgamewiki.object.HomeCommand;
import org.sejong.sulgamewiki.object.HomeDto;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

  private final BasePostRepository basePostRepository;

  public HomeDto getHomepage(HomeCommand command) {

    /*
    최신게시물
   */
    // 최신 창작게시물 가져오기 (Slice 방식)
    List<CreationGame> latestCreationGames = basePostRepository.findCreationGames(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "createdDate")
        )).getContent();

    // 최신 인트로 게시물 가져오기 (Slice 방식)
    List<Intro> latestIntros = basePostRepository.findIntros(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "createdDate")
        )).getContent();

    /*
    국룰술게임
    */
    // 좋아요 순으로 오피셜 게임 가져오기
    List<OfficialGame> officialGamesByLikes = basePostRepository.findOfficialGames(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "likes")
        )).getContent();

    /*
    실시간 ㅅㄱㅇㅋ차트
     */
    // 실시간 창작술게임 차트 가져오기
    List<CreationGame> creationGamesByRealTimeScore = basePostRepository.findCreationGames(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "dailyScore")
        )).getContent();

    // 실시간 인트로 차트 가져오기
    List<Intro> introsByRealTimeScore = basePostRepository.findIntros(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "dailyScore")
        )).getContent();

    // 실시간 공식술게임 차트 가져오기
    List<OfficialGame> officialGamesByRealTimeScore = basePostRepository.findOfficialGames(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "dailyScore")
        )).getContent();

    /*
    인트로 자랑하기
     */
    // 좋아요 순으로 인트로 자랑하기 데이터 가져오기
    List<Intro> introsByLikes = basePostRepository.findIntros(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "likes")
        )).getContent();

    // 조회수 순으로 인트로 자랑하기 데이터 가져오기
    List<Intro> introsByViews = basePostRepository.findIntros(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "views")
        )).getContent();

    /*
    오늘 가장 핫했던 술게임
     */
    // 오늘 가장 핫했던 게임 가져오기
    List<BasePost> gamesByDailyScore = basePostRepository.findGames(
        PageRequest.of(
            command.getPageNumber(),
            command.getPageSize(),
            Sort.by(Sort.Direction.DESC, "weeklyScore")
        )).getContent();

    // 빌더 패턴을 사용하여 HomeDto 생성
    return HomeDto.builder()
        // 최신 게시물 설정
        .latestCreationGames(latestCreationGames)
        .latestIntros(latestIntros)

        // 국룰술게임 설정
        .officialGamesByLikes(officialGamesByLikes)

        // 실시간 ㅅㄱㅇㅋ차트 설정
        .creationGamesByDailyScore(creationGamesByRealTimeScore)
        .introsByDailyScore(introsByRealTimeScore)
        .officialGamesByDailyScore(officialGamesByRealTimeScore)

        // 인트로 자랑하기 설정
        .introsByLikes(introsByLikes)
        .introsByViews(introsByViews)

        // 오늘 가장 핫했던 술게임 설정
        .gamesByWeeklyScore(gamesByDailyScore)

        // HomeDto 빌드
        .build();
  }

  public HomeDto getPosts(HomeCommand command) {
    Pageable pageable = createPageable(command);

    Slice<CreationGame> creationGamesSlice = null;
    Slice<Intro> introsSlice = null;
    Slice<OfficialGame> officialGamesSlice = null;
    Slice<BasePost> GamesSlice = null;

    switch (command.getPostType()) {
      case CREATION_GAME:
        creationGamesSlice = basePostRepository.findCreationGames(pageable);
        return HomeDto.builder()
            .latestCreationGames(creationGamesSlice.getContent())
            .hasNext(creationGamesSlice.hasNext())
            .build();
      case INTRO:
        introsSlice = basePostRepository.findIntros(pageable);
        return HomeDto.builder()
            .latestIntros(introsSlice.getContent())
            .hasNext(introsSlice.hasNext())
            .build();
      case OFFICIAL_GAME:
        officialGamesSlice = basePostRepository.findOfficialGames(pageable);
        return HomeDto.builder()
            .officialGamesByLikes(officialGamesSlice.getContent())
            .hasNext(officialGamesSlice.hasNext())
            .build();
      case GAMES:
        GamesSlice = basePostRepository.findGames(pageable);
        return HomeDto.builder()
            .gamesByWeeklyScore(GamesSlice.getContent())
            .hasNext(GamesSlice.hasNext())
            .build();
      default:
        throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }
  }

  // 정렬 기준에 따른 Pageable 객체 생성
  private Pageable createPageable(HomeCommand command) {
    return PageRequest.of(
        command.getPageNumber(),
        command.getPageSize(),
        Sort.by(command.getDirection(), command.getSortBy().getValue())
    );
  }
}
