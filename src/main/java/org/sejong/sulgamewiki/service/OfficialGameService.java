package org.sejong.sulgamewiki.service;


import static org.sejong.sulgamewiki.object.BasePost.checkCreatorInfoIsPrivate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.Intro;
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
            .creatorInfoIsPrivate(checkCreatorInfoIsPrivate(command.getCreatorInfoIsPrivate()))
            .gameTags(command.getGameTags())
            .build());

    command.setBasePost((savedOfficialGame));

    List<BaseMedia> savedMedias = baseMediaService.uploadMedias(command);

    return BasePostDto.builder()
        .officialGame(savedOfficialGame)
        .baseMedias(savedMedias)
        .build();
  }

  @Transactional(readOnly = true)
  public BasePostDto getOfficialGame(BasePostCommand command) {

    OfficialGame officialGame = basePostRepository.findOfficialGameByBasePostId(
        command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    List<BaseMedia> medias = basePostRepository.findMediasByBasePostId(
        command.getBasePostId());

    return BasePostDto.builder()
        .officialGame(officialGame)
        .baseMedias(medias)
        .build();
  }

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
    existingOfficialGame.setThumbnailIcon(command.getThumbnailIcon());
    existingOfficialGame.setCreatorInfoIsPrivate(command.getCreatorInfoIsPrivate());
    existingOfficialGame.setGameTags(command.getGameTags());

    existingOfficialGame.markAsUpdated();
    command.setBasePost(existingOfficialGame);

    List<BaseMedia> updatedMedias = baseMediaService.updateMedias(command);
    // 게시글과 미디어 파일 저장
    basePostRepository.save(existingOfficialGame);

    return BasePostDto.builder()
        .officialGame(existingOfficialGame)
        .baseMedias(updatedMedias)
        .build();
  }

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

    // 게시글을 삭제된 것으로 표시
    officialGame.markAsDeleted();
    // 변경사항을 저장
    basePostRepository.save(officialGame);
  }


//  public ReportDto reportGame(ReportCommand reportCommand) {
//    OfficialGame officialGame = (OfficialGame) basePostRepository.findById(reportCommand.getSourceId())
//        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));
//
//    Member member = memberRepository.findById(reportCommand.getMemberId())
//        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//
//    // 중복 신고 여부 확인
//    boolean isAlreadyReported = reportService.isAlreadyReported(
//        member, reportCommand.getSourceId(), SourceType.OFFICIAL_GAME);
//    if (isAlreadyReported) {
//      throw new CustomException(ErrorCode.ALREADY_REPORTED);
//    }
//
//    // 리포트 생성
//    ReportCommand command = ReportCommand.builder()
//        .memberId(member.getMemberId())
//        .sourceId(officialGame.getBasePostId())
//        .sourceType(SourceType.OFFICIAL_GAME)
//        .reportType(reportCommand.getReportType())
//        .build();
//
//    reportService.createReport(command);
//    return null;
//  }
}