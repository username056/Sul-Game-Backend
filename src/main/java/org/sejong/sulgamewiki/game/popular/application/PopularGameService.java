package org.sejong.sulgamewiki.game.popular.application;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.common.aws_s3.application.S3Service;
import org.sejong.sulgamewiki.common.entity.BaseMedia;
import org.sejong.sulgamewiki.common.entity.constants.BasePostSource;
import org.sejong.sulgamewiki.common.entity.constants.MediaType;
import org.sejong.sulgamewiki.common.entity.repository.BaseMediaRepository;
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
    PopularGame popularGame = PopularGame.toEntity(member, request);
    // PopularEntity DB 저장
    PopularGame savedPopularGame = popularGameRepository.save(popularGame);

    List<String> popularGameMediaUrls
        = s3Service.uploadFiles(multipartFiles, BasePostSource.POPULAR_GAME);

    for( MultipartFile file : multipartFiles) {
      String fileUrl = s3Service.uploadFile(file, BasePostSource.POPULAR_GAME);

      BaseMedia baseMedia = BaseMedia.builder()
          .mediaUrl(fileUrl)
          .fileSize(file.getSize())
          .mediaType(MediaType.getMediaType(file))
          .basePost(savedPopularGame)
          .build();

      baseMediaRepository.save(baseMedia);
    }

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
