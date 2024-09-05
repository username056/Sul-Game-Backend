package org.sejong.sulgamewiki.service;


import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfficialGameService {

  private final MemberRepository memberRepository;
  private final BaseMediaRepository baseMediaRepository;
  private final BasePostRepository basePostRepository;
  private final BaseMediaService baseMediaService;
  private final ReportService reportService;

  /**
   *
   * @param command
   * Long memberId
   * String introduction
   * String title
   * String description
   * List<MulipartFile> mulipartfiles
   * @return
   */

  @Transactional
  public BasePostDto createOfficialGame(BasePostCommand command) {

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    OfficialGame savedOfficialGame = basePostRepository.save(
        OfficialGame.builder()
            .isDeleted(false)
            .isUpdated(false)
            .title(command.getTitle())
            .introduction(command.getIntroduction())
            .description(command.getDescription())
            .likes(0)
            .likedMemberIds(new HashSet<>())
            .views(0)
            .reportedCount(0)
            .member(member)
            .dailyScore(0)
            .weeklyScore(0)
            .sourceType(SourceType.OFFICIAL_GAME)
            .thumbnailIcon(command.getThumbnailIcon())
            .isCreatorInfoPrivate(false)
            .gameTags(command.getGameTags())
            .build());

    command.setBasePost((savedOfficialGame));

    List<BaseMedia> savedMedias = baseMediaService.uploadMediasFromGame(command);
    BaseMedia savedIntroMedia = baseMediaService.uploadIntroMediaFromGame(command);

    return BasePostDto.builder()
        .officialGame(savedOfficialGame)
        .baseMedias(savedMedias)
        .introMediaFromGame(savedIntroMedia)
        .build();
  }

  @Transactional(readOnly = true)
  public BasePostDto getOfficialGame(BasePostCommand command) {

    OfficialGame officialGame = basePostRepository.findOfficialGameByBasePostId(
        command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    List<BaseMedia> medias = basePostRepository.findMediasByBasePostId(
        command.getBasePostId());

    BaseMedia introMediaFileInGamePost = baseMediaRepository.findByMediaUrl(command.getIntroMediaFileInGamePostUrl());

    return BasePostDto.builder()
        .officialGame(officialGame)
        .baseMedias(medias)
        .introMediaFromGame(introMediaFileInGamePost)
        .build();
  }

  @Transactional
  public BasePostDto updateOfficialGame(BasePostCommand command) {

    OfficialGame existingOfficialGame = basePostRepository.findOfficialGameByBasePostId(
            command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    Member requestingMember = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    if (!existingOfficialGame.getMember().equals(requestingMember)) {
      // 작성자가 아니거나 권한이 없는 경우 예외 발생
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
    }

    // 기존 게임 정보 업데이트
    existingOfficialGame.setTitle(command.getTitle());
    existingOfficialGame.setIntroduction(command.getIntroduction());
    existingOfficialGame.setDescription(command.getDescription());
    existingOfficialGame.setIntroLyricsInGamePost(command.getIntroLyricsInGamePost());
    // 인트로 미디어
    existingOfficialGame.setThumbnailIcon(command.getThumbnailIcon());
    existingOfficialGame.setGameTags(command.getGameTags());
    existingOfficialGame.setCreatorInfoPrivate(command.getIsCreatorInfoPrivate());


    existingOfficialGame.markAsUpdated();
    command.setBasePost(existingOfficialGame);

    List<BaseMedia> updatedMedias = baseMediaService.updateMedias(command);
    // 게시글과 미디어 파일 저장
    BaseMedia updatedIntroMediaFromGame = baseMediaService.updateMedias(command).get(0);

    basePostRepository.save(existingOfficialGame);

    return BasePostDto.builder()
        .officialGame(existingOfficialGame)
 //       .baseMedias(updatedIntroMediaFromGame)
        .build();
  }

  @Transactional
  public void deleteOfficialGame(BasePostCommand command) {
    OfficialGame officialGame = basePostRepository.findOfficialGameByBasePostId(
            command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    Member requestingMember = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    if (!officialGame.getMember().equals(requestingMember)) {
      // 작성자가 아니거나 권한이 없는 경우 예외 발생
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
    }

    // 연관된 미디어 파일을 실제로 삭제
    List<BaseMedia> medias = baseMediaRepository.findAllByBasePost_BasePostId(
        command.getBasePostId());
    if (medias != null && !medias.isEmpty()) {
      baseMediaService.deleteMedias(medias);
    }

    // 게시글을 삭제된 것으로 표시
    officialGame.markAsDeleted();

    // 변경사항을 저장
    basePostRepository.save(officialGame);
  }

}