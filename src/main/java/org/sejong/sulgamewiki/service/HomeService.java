package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.HomeDto;
import org.sejong.sulgamewiki.object.OfficialGameDto;
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
    // 최신 게시물 가져오기 (Slice 방식)
    Slice<BasePostDto> latestPostsSlice = basePostRepository.findLatestPosts(pageable);
    List<BasePostDto> latestBasePostDtoList = latestPostsSlice.getContent();

    // 좋아요 순으로 오피셜 게임 가져오기
    Slice<OfficialGameDto> officialGames = basePostRepository.findOfficialGamesByLikes(pageable);
    List<OfficialGameDto> officialGameDtoList = officialGames.getContent();

    // 실시간 차트 가져오기

    // 인트로 자랑하기 데이터 가져오기

    // 오늘 가장 핫했던 게임 가져오기
    //TODO: "핫"하다는 기준 세우기(게시글 score)

    // HomeDto에 담아서 반환
    return HomeDto.builder()
        .latestPosts(latestBasePostDtoList)
        .officialGames(officialGameDtoList)
        .build();
  }
}
