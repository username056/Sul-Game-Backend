package org.sejong.sulgamewiki.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.util.S3Service;
import org.sejong.sulgamewiki.util.entity.BaseMedia;
import org.sejong.sulgamewiki.object.constants.BasePostSource;
import org.sejong.sulgamewiki.util.entity.constants.MediaType;
import org.sejong.sulgamewiki.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.game.common.exception.GameErrorCode;
import org.sejong.sulgamewiki.game.common.exception.GameException;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.repository.PopularGameRepository;
import org.sejong.sulgamewiki.game.popular.dto.request.CreatePopularGameRequest;
import org.sejong.sulgamewiki.game.popular.dto.response.CreatePopularGameResponse;
import org.sejong.sulgamewiki.game.popular.dto.response.GetPopularGameResponse;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class PopularGameService {

  private final MemberRepository memberRepository;
  private final PopularGameRepository popularGameRepository;
  private final BaseMediaRepository baseMediaRepository;
  private final S3Service s3Service;

  @Transactional
  public CreatePopularGameResponse createPopularGame(
      Long memberId,
      CreatePopularGameRequest request,
      List<MultipartFile> multipartFiles
      ) {
    // Member 정보 가져오기
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    // CreatePopularGameRequest, Member 로 -> PopularEntity 객체 생성
    OfficialGame officialGame = OfficialGame.toEntity(member, request);

    // PopularEntity DB 저장
    OfficialGame savedOfficialGame = popularGameRepository.save(officialGame);

    for( MultipartFile file : multipartFiles) {
      String fileUrl = s3Service.uploadFile(file, BasePostSource.POPULAR_GAME);

      BaseMedia baseMedia = BaseMedia.builder()
          .mediaUrl(fileUrl)
          .fileSize(file.getSize())
          .mediaType(MediaType.getMediaType(file))
          .basePost(savedOfficialGame)
          .build();

      baseMediaRepository.save(baseMedia);
    }

    // CreatePopularGameResponse DTO 로 변환
    return CreatePopularGameResponse.from(savedOfficialGame);
  }

  public GetPopularGameResponse getPopularGame(Long id) {
    OfficialGame officialGame = popularGameRepository.findById(id)
        .orElseThrow(() -> new GameException(GameErrorCode.GAME_NOT_FOUND));
    return GetPopularGameResponse.from(officialGame);
  }

  // 단일 객체 객체 가져오는거 -> 상세보기


  // 여러 객체 가져오는거 -> List 보기

 // @Transactional
//  public UpdatePopularGameResponse updatePopularGame(
//      Long popularGameId,
//      Long memberId,
//      UpdatePopularGameRequest request) {
//    Member member = memberRepository.findById(memberId)
//        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
//
//    PopularGame popularGame = popularGameRepository.findById(popularGameId)
//        .orElseThrow(() -> new GameException(GameErrorCode.GAME_NOT_FOUND));
//    // 멤버 체크(Admin 여부 체크), 존재하는 게임 확인,
//
//    PopularGameMedia popularGameMedia= popularGameMediaRepository.findBy
//
//    popularGame.setTitle(request.getTitle());
//    popularGame.setIntroduction(request.getIntroduction());
//    popularGame.setDescription(request.getDescription());
//    // 업데이트
//
//
//
//
//
//
//    popularGameRepository.save(popularGame);
//    // 객체 업데이트, 리스폰스dto 반환
//    return UpdatePopularGameResponse.from(popularGame);
//  }



}
