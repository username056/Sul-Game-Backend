package org.sejong.sulgamewiki.game.popular.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.game.common.exception.GameErrorCode;
import org.sejong.sulgamewiki.game.common.exception.GameException;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;
import org.sejong.sulgamewiki.game.popular.domain.repository.PopularGameRepository;
import org.sejong.sulgamewiki.game.popular.dto.request.CreatePopularGameRequest;
import org.sejong.sulgamewiki.game.popular.dto.response.CreatePopularGameResponse;
import org.sejong.sulgamewiki.game.popular.dto.response.GetPopularGameResponse;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PopularGameService {

  private final MemberRepository memberRepository;
  private final PopularGameRepository popularGameRepository;

  public CreatePopularGameResponse createPopularGame(
      Long memberId,
      CreatePopularGameRequest request) {
    // Member 정보 가져오기
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    // CreatePopularGameRequest, Member 로 -> PopularEntity 객체 생성
    PopularGame popularGame = PopularGame.toEntity(member, request);

    // PopularEntity DB 저장
    PopularGame savedPopularGame = popularGameRepository.save(popularGame);

    //TODO: List<MultipartFIle> files -> AWS S3 -> URL 받아오는 로직 필요
    //TODO: PopularGameMedia 생성 로직 필요

    // CreatePopularGameResponse DTO 로 변환
    return CreatePopularGameResponse.from(savedPopularGame);
  }

  public GetPopularGameResponse getPopularGame(Long id) {
    PopularGame popularGame = popularGameRepository.findById(id)
        .orElseThrow(() -> new GameException(GameErrorCode.GAME_NOT_FOUND));
    return GetPopularGameResponse.from(popularGame);
  }

  // 단일 객체 객체 가져오는거 -> 상세보기

  // 여러 객체 가져오는거 -> List 보기
}
