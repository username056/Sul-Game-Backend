package org.sejong.sulgamewiki.intro.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.common.aws_s3.application.S3Service;
import org.sejong.sulgamewiki.common.entity.BaseMedia;
import org.sejong.sulgamewiki.common.entity.constants.BasePostSource;
import org.sejong.sulgamewiki.common.entity.constants.MediaType;
import org.sejong.sulgamewiki.common.entity.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.intro.domain.entity.Intro;
import org.sejong.sulgamewiki.intro.domain.repository.IntroRepository;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.sejong.sulgamewiki.intro.dto.response.CreateIntroResponse;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntroService {

  private final MemberRepository memberRepository;
  private final IntroRepository introRepository;
  private final BaseMediaRepository baseMediaRepository;
  private final S3Service s3Service;

  public CreateIntroResponse createIntro(Long memberId,
      CreateIntroRequest request, List<MultipartFile> files) {
    // Member 정보 가져오기
    Member member = memberRepository.findById(memberId)
        .orElseThrow(
            () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    // CreateIntroRequest, Member 로 -> Intro 엔티티 객체 생성
    Intro intro = Intro.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .lyrics(request.getLyrics())
        .member(member)
        .likes(0)
        .views(0)
        .build();

    // Intro 엔티티 DB 저장
    Intro savedIntro = introRepository.save(intro);

    for (MultipartFile file : files) {
        String fileUrl = s3Service.uploadFile(file, BasePostSource.INTRO);
        BaseMedia introMedia = BaseMedia.builder()
            .mediaUrl(fileUrl)
            .fileSize(file.getSize())
            .mediaType(MediaType.getMediaType(file))
            .basePost(savedIntro)
            .build();
        // IntroMedia 엔티티를 데이터베이스에 저장
        baseMediaRepository.save(introMedia);
    }

    // CreateIntroResponse DTO 로 변환
    return CreateIntroResponse.from(savedIntro);
  }


}
