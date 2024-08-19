package org.sejong.sulgamewiki.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.IntroCommand;
import org.sejong.sulgamewiki.object.IntroDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.object.constants.MediaType;
import org.sejong.sulgamewiki.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.S3Service;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntroService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final BaseMediaRepository baseMediaRepository;
  private final S3Service s3Service;

  public IntroDto createIntro(IntroCommand command) {
    IntroDto dto = IntroDto.builder().build();
    List<BaseMedia> baseMedias = new ArrayList<>();

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Intro intro = Intro.builder()
        .title(command.getTitle())
        .lyrics(command.getLyrics())
        .description(command.getDescription())
        .likes(0)
        .views(0)
        .member(member)
        .build();

    Intro savedIntro = basePostRepository.save(intro);

    for (MultipartFile file : command.getMultipartFiles()) {
      String fileUrl = s3Service.uploadFile(file, SourceType.INTRO);

      BaseMedia introMedia = BaseMedia.builder()
          .mediaUrl(fileUrl)
          .fileSize(file.getSize())
          .mediaType(MediaType.getMediaType(file))
          .basePost(savedIntro)
          .build();

      baseMedias.add(baseMediaRepository.save(introMedia));
    }

    dto.setBasePost(savedIntro);
    dto.setBaseMedias(baseMedias);
    return dto;
  }
}
