package org.sejong.sulgamewiki.service;


import static org.sejong.sulgamewiki.object.BasePost.checkCreatorInfoIsPrivate;
import static org.sejong.sulgamewiki.object.constants.ExpRule.POST_CREATION;
import static org.sejong.sulgamewiki.object.constants.ExpRule.POST_DELETION;

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
  private final ExpManagerService expManagerService;
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
            .commentCount(0)
            .sourceType(SourceType.OFFICIAL_GAME)
            .thumbnailIcon(command.getThumbnailIcon())
            .isCreatorInfoPrivate(false)
            .gameTags(command.getGameTags())
            .build());

    command.setBasePost((savedOfficialGame));
    command.setBasePostId(savedOfficialGame.getBasePostId());

    List<BaseMedia> savedMedias = baseMediaService.uploadMediasFromGame(command);
    BaseMedia savedIntroMedia = baseMediaService.uploadIntroMediaFromGame(command);

    // 창작자 점수 올리는 메서드
    expManagerService.updateExp(member, POST_CREATION);

    return BasePostDto.builder()
        .officialGame(savedOfficialGame)
        .baseMedias(savedMedias)
        .introMediaInGame(savedIntroMedia)
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
    //TODO: 해당 포스트와 연관된 창작 술게임 가져와야함

    return BasePostDto.builder()
        .officialGame(officialGame)
        .baseMedias(medias)
        .introMediaInGame(introMediaFileInGamePost)
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
    existingOfficialGame.setCreatorInfoPrivate(checkCreatorInfoIsPrivate(command.getIsCreatorInfoPrivate()));
    // 업데이트시 널값 들어오면 자동으로 공개로 설정되도록 수정


    existingOfficialGame.markAsUpdated();
    command.setBasePost(existingOfficialGame);

    List<BaseMedia> updatedMedias = baseMediaService.updateMedias(command);
    // 게시글과 미디어 파일 저장



    // 태그는 4개 이하 에러코드 만들기
    basePostRepository.save(existingOfficialGame);

    BaseMedia introMediaFileInGamePost = baseMediaRepository.findByMediaUrl(
        existingOfficialGame.getIntroMediaFileInGamePostUrl());


    return BasePostDto.builder()
        .officialGame(existingOfficialGame)
        .baseMedias(updatedMedias)
        .introMediaFileInGamePost(introMediaFileInGamePost)
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
    officialGame.setIntroMediaFileInGamePostUrl(null);

    // 창작자 경험치 회수
    expManagerService.updateExp(requestingMember, POST_DELETION);

    // 변경사항을 저장
    basePostRepository.save(officialGame);
  }

}