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
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeService {

  private final BasePostRepository basePostRepository;

  public HomeDto getHomeInfo(HomeCommand command) {
    Pageable pageable = command.getPageable();

    /*
    최신게시물
   */
    // 최신 창작게시물 가져오기 (Slice 방식)
    Slice<CreationGame> latestCreativeGamesSlice = basePostRepository.findLatestCreativeGames(pageable);
    List<CreationGame> latestCreationGameList = latestCreativeGamesSlice.getContent();

    // 최신 인트로게시물 가져오기 (Slice 방식)
    Slice<Intro> latestIntrosSlice = basePostRepository.findLatestIntros(pageable);
    List<Intro> latestIntrosList = latestIntrosSlice.getContent();

    /*
    국룰술게임
    */
    // 좋아요 순으로 오피셜 게임 가져오기
    Slice<OfficialGame> officialGames = basePostRepository.findOfficialGamesByLikes(pageable);
    List<OfficialGame> officialGameList = officialGames.getContent();

    /*
    실시간 ㅅㄱㅇㅋ차트
     */
    // 실시간 창작술게임 차트 가져오기
    Slice<CreationGame> creativeGamesByRealTimeScore = basePostRepository.findCreativeGamesByDailyScore(pageable);
    List<CreationGame> creationGameListByRealTimeScore = creativeGamesByRealTimeScore.getContent();

    // 실시간 인트로 차트 가져오기
    Slice<Intro> introsByRealTimeScore = basePostRepository.findIntrosByDailyScore(pageable);
    List<Intro> introsListByRealTimeScore = introsByRealTimeScore.getContent();

    // 실시간 공식술게임 차트 가져오기
    Slice<OfficialGame> officialGamesByRealTimeScore = basePostRepository.findOfficialGamesByDailyScore(pageable);
    List<OfficialGame> officialGameListByRealTimeScore = officialGamesByRealTimeScore.getContent();

    /*
    인트로 자랑하기
     */
    // 좋아요순으로 인트로 자랑하기 데이터 가져오기
    Slice<Intro> introsSliceByLikes = basePostRepository.findIntrosByLikes(pageable);
    List<Intro> introsListByLikes = introsSliceByLikes.getContent();

    // 조회수순으로 인트로 자랑하기 데이터 가져오기
    Slice<Intro> introsByViews = basePostRepository.findByViews(pageable);
    List<Intro> introsListByViews = introsByViews.getContent();

    /*
    오늘 가장 핫했던 술게임
     */
    // 오늘 가장 핫했던 게임 가져오기
    Slice<BasePost> postsSliceByDailyScore = basePostRepository.findPostsByWeeklyScore(pageable);
    List<BasePost> postsListByDailyScore = postsSliceByDailyScore.getContent();

    // 빌더 패턴을 사용하여 HomeDto 생성
    return HomeDto.builder()
        // 최신 게시물 설정
        .latestCreationList(latestCreationGameList)
        .latestIntroList(latestIntrosList)

        // 국룰술게임 설정
        .officialGames(officialGameList)

        // 실시간 ㅅㄱㅇㅋ차트 설정
        .creationGameDailyChart(creationGameListByRealTimeScore)
        .introDailyChart(introsListByRealTimeScore)
        .officialGameDailyChart(officialGameListByRealTimeScore)

        // 인트로 자랑하기 설정
        .introsByLikes(introsListByLikes)
        .introsByViews(introsListByViews)

        // 오늘 가장 핫했던 술게임 설정
        .weeklyHotGames(postsListByDailyScore)

        // HomeDto 빌드
        .build();
  }
}
