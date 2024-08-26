package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.CreationGame;
import org.sejong.sulgamewiki.object.HomeCommand;
import org.sejong.sulgamewiki.object.HomeDto;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

  private final BasePostRepository basePostRepository;

  public HomeDto getHomeInfo(HomeCommand command) {
    Pageable pageable = command.getPageable();

    /*
    최신게시물
   */
    // 최신 창작게시물 가져오기 (Slice 방식)
    List<CreationGame> latestCreationGames = basePostRepository.findLatestCreationGames(pageable).getContent();

    // 최신 인트로게시물 가져오기 (Slice 방식)
    List<Intro> latestIntros = basePostRepository.findLatestIntros(pageable).getContent();

    /*
    국룰술게임
    */
    // 좋아요 순으로 오피셜 게임 가져오기
    List<OfficialGame> officialGamesByLikes = basePostRepository.findOfficialGamesByLikes(pageable).getContent();

    /*
    실시간 ㅅㄱㅇㅋ차트
     */
    // 실시간 창작술게임 차트 가져오기
    List<CreationGame> creationGamesByRealTimeScore = basePostRepository.findCreationGamesByDailyScore(pageable).getContent();

    // 실시간 인트로 차트 가져오기
    List<Intro> introsByRealTimeScore = basePostRepository.findIntrosByDailyScore(pageable).getContent();

    // 실시간 공식술게임 차트 가져오기
    List<OfficialGame> officialGamesByRealTimeScore = basePostRepository.findOfficialGamesByDailyScore(pageable).getContent();

    /*
    인트로 자랑하기
     */
    // 좋아요순으로 인트로 자랑하기 데이터 가져오기
    List<Intro> introsByLikes = basePostRepository.findIntrosByLikes(pageable).getContent();

    // 조회수순으로 인트로 자랑하기 데이터 가져오기
    List<Intro> introsByViews = basePostRepository.findIntrosByViews(pageable).getContent();

    /*
    오늘 가장 핫했던 술게임
     */
    // 오늘 가장 핫했던 게임 가져오기
    List<BasePost> gamesByDailyScore = basePostRepository.findGamesByWeeklyScore(pageable).getContent();

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
}
