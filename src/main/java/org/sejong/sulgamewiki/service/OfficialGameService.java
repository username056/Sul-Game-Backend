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
import org.sejong.sulgamewiki.object.constants.BasePostSource;
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
      String fileUrl = s3Service.uploadFile(file, BasePostSource.POPULAR_GAME);

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
}
