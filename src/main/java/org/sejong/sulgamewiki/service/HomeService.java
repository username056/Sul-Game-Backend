package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.CreativeGame;
import org.sejong.sulgamewiki.object.HomeDto;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

  private final BasePostRepository basePostRepository;

  public HomeDto getHomeData(Pageable pageable) {

    /*--------------------------최신게시물---------------------------*/
    // 최신 창작게시물 가져오기 (Slice 방식)
    Slice<CreativeGame> latestCreativeGamesSlice = basePostRepository.findLatestCreativeGames(
        pageable);
    List<CreativeGame> latestCreativeGameList = latestCreativeGamesSlice.getContent();

    // 최신 인트로게시물 가져오기 (Slice 방식)
    Slice<Intro> latestIntrosSlice = basePostRepository.findLatestIntros(
        pageable);
    List<Intro> latestIntrosList = latestIntrosSlice.getContent();




    /*--------------------------국룰술게임---------------------------*/
    // 좋아요 순으로 오피셜 게임 가져오기
    Slice<OfficialGame> officialGames = basePostRepository.findOfficialGamesByLikes(
        pageable);
    List<OfficialGame> officialGameList = officialGames.getContent();




    /*--------------------------실시간 ㅅㄱㅇㅋ차트---------------------------*/
    // 실시간 창작술게임 차트 가져오기
    Slice<CreativeGame> creativeGamesByRealTimeScore = basePostRepository.findCreativeGamesByRealTimeScore(pageable);
    List<CreativeGame> creativeGameListByRealTimeScore = creativeGamesByRealTimeScore.getContent();

    // 실시간 인트로 차트 가져오기
    Slice<Intro> introsByRealTimeScore = basePostRepository.findIntrosByRealTimeScore(pageable);
    List<Intro> introsListByRealTimeScore = introsByRealTimeScore.getContent();

    // 실시간 공식술게임 차트 가져오기
    Slice<OfficialGame> officialGamesByRealTimeScore = basePostRepository.findOfficialGamesByRealTimeScore(pageable);
    List<OfficialGame> officialGameListByRealTimeScore = officialGamesByRealTimeScore.getContent();




    /*--------------------------인트로 자랑하기---------------------------*/
    // 최신순으로 인트로 자랑하기 데이터 가져오기(위에 재활용)

    // 좋아요순으로 인트로 자랑하기 데이터 가져오기
    Slice<Intro> introsSliceByLikes = basePostRepository.findIntrosByLikes(pageable);
    List<Intro> introsListByLikes = introsSliceByLikes.getContent();

    // 조회수순으로 인트로 자랑하기 데이터 가져오기
    Slice<Intro> introsByViews = basePostRepository.findByViews(pageable);
    List<Intro> introsListByViews = introsByViews.getContent();


    /*--------------------------오늘 가장 핫했던 술게임---------------------------*/
    // 오늘 가장 핫했던 게임 가져오기
    //TODO: "핫"하다는 기준 세우기(게시글 score)
    Slice<BasePost> postsSliceByDailyScore = basePostRepository.findPostsByDailyScore(pageable);
    List<BasePost> postsListByDailyScore = postsSliceByDailyScore.getContent();



    // HomeDto에 담아서 반환
    return HomeDto.builder()
        //최신게시물
        .latestCreativeList(latestCreativeGameList)
        .latestIntroList(latestIntrosList)
        //국룰술게임
        .officialGames(officialGameList)
        //실시간 ㅅㄱㅇㅋ차트
        .creativeGameRealTimeChart(creativeGameListByRealTimeScore)
        .introRealTimeChart(introsListByRealTimeScore)
        .officialGameRealTimeChart(officialGameListByRealTimeScore)
        //인트로 자랑하기
        .introsByLikes(introsListByLikes)
        .introsByViews(introsListByViews)
        //오늘 가장 핫했던 술게임
        .hotGamesToday(postsListByDailyScore)
        .build();
  }
}
