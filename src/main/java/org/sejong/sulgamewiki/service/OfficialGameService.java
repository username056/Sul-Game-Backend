package org.sejong.sulgamewiki.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
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

//  public OfficialGameService(MemberRepository memberRepository,
//      BasePostRepository basePostRepository, ReportService reportService,
//      BaseMediaRepository baseMediaRepository, BaseMediaService baseMediaService) {
//    super(memberRepository, basePostRepository, reportService);
//    this.memberRepository = memberRepository;
//    this.baseMediaRepository = baseMediaRepository;
//    this.basePostRepository = basePostRepository;
//    this.baseMediaService = baseMediaService;
//    this.reportService = reportService;
//  }


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
            .build());

    command.setSourceType(savedOfficialGame.getSourceType());
    command.setBasePost((savedOfficialGame));

    List<BaseMedia> savedMedias = baseMediaService.uploadMedias(command);

    return BasePostDto.builder()
        .officialGame(savedOfficialGame)
        .baseMedias(savedMedias)
        .build();
  }

  public BasePostDto getOfficialGame(BasePostCommand command) {
    BasePostDto dto = BasePostDto.builder().build();

    BasePost officialGame = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    dto.setBasePost(officialGame);
    return dto;
  }

  public BasePostDto updateOfficialGame(Long gameId, BasePostCommand command) {
    BasePostDto dto = BasePostDto.builder().build();
    List<BaseMedia> baseMedias = new ArrayList<>();

    // 게임 정보 찾기
    BasePost basePost = basePostRepository.findById(gameId)
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    if (!(basePost instanceof OfficialGame existingOfficialGame)) {
      throw new CustomException(ErrorCode.GAME_NOT_FOUND);
    }

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 기존 게임 정보 업데이트
    existingOfficialGame.setTitle(command.getTitle());
    existingOfficialGame.setIntroduction(command.getIntroduction());
    existingOfficialGame.setDescription(command.getDescription());

    OfficialGame savedOfficialGame = basePostRepository.save(existingOfficialGame);
    List<String> existingMediaUrls = baseMediaRepository.findMediaUrlsByBasePost_BasePostId(gameId);

    // 새 미디어 파일 처리 및 기존 미디어와 비교 후 업데이트
    if (command.getMultipartFiles() != null) {
      List<String> updatedMediaUrls = baseMediaService.compareAndUpdateMedias(existingMediaUrls, command.getMultipartFiles(), SourceType.OFFICIAL_GAME);

      // 기존 미디어 업데이트된 미디어로 교체
      for (String mediaUrl : updatedMediaUrls) {
        BaseMedia officialGameMedia = BaseMedia.builder()
            .mediaUrl(mediaUrl)
            .basePost(savedOfficialGame)
            .build();

        baseMedias.add(baseMediaRepository.save(officialGameMedia));
      }
    }

    dto.setBasePost(savedOfficialGame);
    dto.setBaseMedias(baseMedias);
    return dto;
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