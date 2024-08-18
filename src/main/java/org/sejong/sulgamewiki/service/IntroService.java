package org.sejong.sulgamewiki.service;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.util.S3Service;
import org.sejong.sulgamewiki.util.entity.constants.MediaType;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.IntroMedia;
import org.sejong.sulgamewiki.intro.domain.repository.IntroMediaRepository;
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
  private final IntroMediaRepository introMediaRepository;
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

    //TODO: List<MultipartFile> files -> AWS S3 -> URL 받아오는 로직 필요
    //TODO: IntroMedia 생성 로직 필요
    // S3에 파일 업로드 및 URL 저장
    for (MultipartFile file : files) {
      try {
        String fileUrl = s3Service.uploadFile(file, "intro");
        IntroMedia introMedia = IntroMedia.builder()
            .mediaUrl(fileUrl)
            .fileSize(file.getSize())
            .mediaType(determineMediaType(file)) // 미디어 타입 결정 로직 추가 필요
            .intro(savedIntro)
            .build();
        // IntroMedia 엔티티를 데이터베이스에 저장
        introMediaRepository.save(introMedia);
      } catch (IOException e) {
        log.error("Failed to upload file to S3", e);
        throw new RuntimeException("Failed to upload file to S3", e);
      }
    }

    // CreateIntroResponse DTO 로 변환
    return CreateIntroResponse.from(savedIntro);
  }

  private MediaType determineMediaType(MultipartFile file) {
    return MediaType.fromMimeType(file.getContentType());
  }
}
