package org.sejong.sulgamewiki.intro.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.intro.domain.entity.Intro;
import org.sejong.sulgamewiki.intro.domain.repository.IntroRepository;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.sejong.sulgamewiki.intro.dto.response.CreateIntroResponse;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntroService {

  private final MemberRepository memberRepository;
  private final IntroRepository introRepository;

  public CreateIntroResponse createIntro(Long memberId,
      CreateIntroRequest request) {
    // Member 정보 가져오기
    Member member = memberRepository.findById(memberId)
        .orElseThrow(
            () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    // CreateIntroRequest, Member 로 -> Intro 엔티티 객체 생성
    Intro intro = Intro.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .member(member)
        .likes(0)
        .views(0)
        .build();

    // Intro 엔티티 DB 저장
    Intro savedIntro = introRepository.save(intro);

    //TODO: List<MultipartFile> files -> AWS S3 -> URL 받아오는 로직 필요
    //TODO: IntroMedia 생성 로직 필요

    // CreateIntroResponse DTO 로 변환
    return CreateIntroResponse.from(savedIntro);
  }
}
