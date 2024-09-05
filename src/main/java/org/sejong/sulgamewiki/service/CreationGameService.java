package org.sejong.sulgamewiki.service;

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

  @Transactional
  public BasePostDto createCreationGame(BasePostCommand command) {

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    OfficialGame officialGame = (OfficialGame) basePostRepository.findById(command.getRelatedOfficialGameId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

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
            .sourceType(SourceType.CREATION_GAME)
            .thumbnailIcon(command.getThumbnailIcon())
            .isCreatorInfoPrivate(command.getIsCreatorInfoPrivate())


            .officialGame(officialGame)

            .build());

    command.setSourceType(savedCreationGame.getSourceType());
    command.setBasePost((savedCreationGame));

    List<BaseMedia> savedCreationMedias = baseMediaService.uploadMediasFromGame(command);

    return BasePostDto.builder()
        .creationGame(savedCreationGame)
        .baseMedias(savedCreationMedias)
        .build();
  }
}
