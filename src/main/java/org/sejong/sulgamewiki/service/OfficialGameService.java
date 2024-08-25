package org.sejong.sulgamewiki.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.OfficialGameCommand;
import org.sejong.sulgamewiki.object.OfficialGameDto;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.constants.ReportType;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.object.constants.MediaType;
import org.sejong.sulgamewiki.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.S3Service;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfficialGameService {

  private final MemberRepository memberRepository;
  private final BaseMediaRepository baseMediaRepository;
  private final BasePostRepository basePostRepository;
  private final S3Service s3Service;
  private final BaseMediaService baseMediaService;
  private final ReportService reportService;

  @Transactional
  public OfficialGameDto createOfficialGame(OfficialGameCommand command) {
    OfficialGameDto dto = OfficialGameDto.builder().build();
    List<BaseMedia> baseMedias = new ArrayList<>();

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    OfficialGame officialGame = OfficialGame.builder()
        .title(command.getTitle())
        .introduction(command.getIntroduction())
        .description(command.getDescription())
        .likes(0)
        .views(0)
        .member(member)
        .build();

    OfficialGame savedOfficialGame = basePostRepository.save(officialGame);

    for( MultipartFile file : command.getMultipartFiles()) {
      String fileUrl = s3Service.uploadFile(file, SourceType.OFFICIAL_GAME);

      BaseMedia officialGameMedia = BaseMedia.builder()
          .mediaUrl(fileUrl)
          .fileSize(file.getSize())
          .mediaType(MediaType.getMediaType(file))
          .basePost(savedOfficialGame)
          .build();

      baseMedias.add(baseMediaRepository.save(officialGameMedia));
    }

    dto.setBasePost(savedOfficialGame);
    dto.setBaseMedias(baseMedias);
    return dto;
  }

  public OfficialGameDto getOfficialGame(OfficialGameCommand command) {
    OfficialGameDto dto = OfficialGameDto.builder().build();

    BasePost officialGame = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    dto.setBasePost(officialGame);
    return dto;
  }

  public OfficialGameDto updateOfficialGame(Long gameId, OfficialGameCommand command) {
    OfficialGameDto dto = OfficialGameDto.builder().build();
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
      List<String> updatedMediaUrls = baseMediaService.compareAndUpdateMedia(existingMediaUrls, command.getMultipartFiles(), SourceType.OFFICIAL_GAME);

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

  public void reportGame(Long gameId, Long memberId, ReportType reportType) {
    OfficialGame officialGame = (OfficialGame) basePostRepository.findById(gameId)
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    // 중복 신고 여부 확인
    boolean isAlreadyReported = reportService.isAlreadyReported(memberId, gameId, SourceType.OFFICIAL_GAME);
    if (isAlreadyReported) {
      throw new CustomException(ErrorCode.ALREADY_REPORTED);
    }

    // 리포트 생성
    ReportCommand command = ReportCommand.builder()
        .memberId(memberId)
        .sourceId(gameId)
        .sourceType(SourceType.OFFICIAL_GAME)
        .reportType(reportType)
        .build();

    reportService.createReport(command);
  }
}