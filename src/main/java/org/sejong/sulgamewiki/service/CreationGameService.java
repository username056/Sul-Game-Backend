package org.sejong.sulgamewiki.service;

import static org.sejong.sulgamewiki.object.BasePost.checkCreatorInfoIsPrivate;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.CreationGame;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.constants.ExpRule;
import org.sejong.sulgamewiki.object.constants.ScoreRule;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreationGameService {

  private final MemberRepository memberRepository;
  private final BaseMediaRepository baseMediaRepository;
  private final BasePostRepository basePostRepository;
  private final BaseMediaService baseMediaService;
  private final ExpManagerService expManagerService;

  @Transactional
  public BasePostDto createCreationGame(BasePostCommand command) {

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    OfficialGame officialGame = (OfficialGame) basePostRepository.findById(command.getRelatedOfficialGameId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    if (command.getGameTags().size() > 4) {
      throw new CustomException(ErrorCode.TAG_LIMIT_EXCEEDED);
    }
    
    CreationGame savedCreationGame = basePostRepository.save(
        CreationGame.builder()
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
            .sourceType(SourceType.CREATION_GAME)
            .thumbnailIcon(command.getThumbnailIcon())
            .isCreatorInfoPrivate(checkCreatorInfoIsPrivate(command.getIsCreatorInfoPrivate()))
            .gameTags(command.getGameTags())
            .officialGame(officialGame)
            .build());

    command.setSourceType(savedCreationGame.getSourceType());
    command.setBasePost((savedCreationGame));

    List<BaseMedia> savedCreationMedias = baseMediaService.uploadMediasFromGame(command);
    BaseMedia savedIntroMeida = baseMediaService.updateIntroMediaFromGame(command);

    expManagerService.updateExp(member, ExpRule.POST_CREATION);

    return BasePostDto.builder()
        .creationGame(savedCreationGame)
        .baseMedias(savedCreationMedias)
        .introMediaInGame(savedIntroMeida)
        .build();
  }

  @Transactional(readOnly = true)
  public BasePostDto getCreationGame(BasePostCommand command) {

    CreationGame creationGame = basePostRepository.findCreationGameByBasePostId(
            command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    List<BaseMedia> medias = basePostRepository.findMediasByBasePostId(
        command.getBasePostId());

    BaseMedia introMediaFileInGamePost = baseMediaRepository.findByMediaUrl(command.getIntroMediaUrlFromGame());

    // 조회수 증가로직
    creationGame.increaseViews();

    // 게시글 Score
    creationGame.updateScore(ScoreRule.VIEW);

    return BasePostDto.builder()
        .creationGame(creationGame)
        .baseMedias(medias)
        .introMediaInGame(introMediaFileInGamePost)
        .build();
  }

  @Transactional
  public BasePostDto updateCreationGame(BasePostCommand command) {

    CreationGame existingCreationGame = basePostRepository.findCreationGameByBasePostId(
            command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    Member requestingMember = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (!existingCreationGame.getMember().equals(requestingMember)) {
      // 작성자가 아니거나 권한이 없는 경우 예외 발생
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
    }

    if (command.getGameTags().size() > 4) {
      throw new CustomException(ErrorCode.TAG_LIMIT_EXCEEDED);
    }


    // 기존 게임 정보 업데이트
    existingCreationGame.setTitle(command.getTitle());
    existingCreationGame.setIntroduction(command.getIntroduction());
    existingCreationGame.setDescription(command.getDescription());
    existingCreationGame.setThumbnailIcon(command.getThumbnailIcon());
    existingCreationGame.setGameTags(command.getGameTags());
    existingCreationGame.setCreatorInfoPrivate(checkCreatorInfoIsPrivate(command.getIsCreatorInfoPrivate()));
    existingCreationGame.setOfficialGame(
        basePostRepository.findOfficialGameByBasePostId(command.getRelatedOfficialGameId())
            .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND))
    );

    existingCreationGame.markAsUpdated();
    command.setBasePost(existingCreationGame);

    List<BaseMedia> updatedMedias = baseMediaService.updateMedias(command);

    basePostRepository.save(existingCreationGame);

    BaseMedia introMediaFileInGamePost = baseMediaRepository.findByMediaUrl(
        existingCreationGame.getIntroMediaFileInGamePostUrl());

    return BasePostDto.builder()
        .creationGame(existingCreationGame)
        .baseMedias(updatedMedias)
        .introMediaFileInGame(introMediaFileInGamePost)
        .build();
  }

  @Transactional
  public void deleteCreationGame(BasePostCommand command) {
    CreationGame creationGame = basePostRepository.findCreationGameByBasePostId(
            command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    Member requestingMember = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (!creationGame.getMember().equals(requestingMember)) {
      // 작성자가 아니거나 권한이 없는 경우 예외 발생
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
    }

    // 연관된 미디어 파일 삭제
    List<BaseMedia> medias = baseMediaRepository.findAllByBasePost_BasePostId(
        command.getBasePostId());
    if (medias != null && !medias.isEmpty()) {
      baseMediaService.deleteMedias(medias);
    }

    // 게시글 삭제 처리
    creationGame.markAsDeleted();
    creationGame.setIntroMediaFileInGamePostUrl(null);

    expManagerService.updateExp(requestingMember, ExpRule.POST_DELETION);

    basePostRepository.save(creationGame);
  }



}
