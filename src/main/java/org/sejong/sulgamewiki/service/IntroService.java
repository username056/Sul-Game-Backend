package org.sejong.sulgamewiki.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.Member;
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
public class IntroService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final BaseMediaRepository baseMediaRepository;
  private final BaseMediaService baseMediaService;

  public BasePostDto createIntro(BasePostCommand command) {

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Intro savedIntro = basePostRepository.save(
        Intro.builder()
            .title(command.getTitle())
            .introduction(command.getIntroduction())
            .lyrics(command.getLyrics())
            .description(command.getDescription())
            .type(command.getIntroType())
            .likes(0)
            .views(0)
            .member(member)
            .sourceType(SourceType.INTRO)
            .build());

    command.setBasePost(savedIntro);
    List<BaseMedia> savedMedias = baseMediaService.uploadMedias(command);

    return BasePostDto.builder()
        .basePost(savedIntro)
        .baseMedias(savedMedias)
        .build();
  }

  // Read: 특정 Intro 조회
  @Transactional(readOnly = true)
  public BasePostDto getIntro(Long basePostId) {
    Intro intro = (Intro) basePostRepository.findByBasePostId(basePostId);
    if (intro == null) {
        throw new CustomException(ErrorCode.POST_NOT_FOUND);
    }

    List<BaseMedia> medias = basePostRepository.findMediasByBasePostId(basePostId);

    return BasePostDto.builder()
        .basePost(intro)
        .baseMedias(medias)
        .build();
  }

  @Transactional(readOnly = true)
  public List<BasePostDto> getAllIntros() {
    List<Intro> intros = basePostRepository.findAllIntros();  // findAllIntros 메서드 구현 필요
    return intros.stream().map(intro -> {
      List<BaseMedia> medias = baseMediaRepository.findAllByBasePost_BasePostId(intro.getBasePostId());
      return BasePostDto.builder()
          .basePost(intro)
          .baseMedias(medias)
          .build();
    }).collect(Collectors.toList());
  }

  @Transactional
  public BasePostDto updateIntro(BasePostCommand command) {
    // 기존 Intro 게시글 조회
    Intro existingIntro = (Intro) basePostRepository.findByBasePostId(command.getBasePostId());
    if (existingIntro == null) {
      throw new CustomException(ErrorCode.POST_NOT_FOUND);
    }

    // 게시글의 제목, 소개글, 가사, 설명 등을 업데이트
    existingIntro.setTitle(command.getTitle());
    existingIntro.setIntroduction(command.getIntroduction());
    existingIntro.setLyrics(command.getLyrics());
    existingIntro.setDescription(command.getDescription());

    // 연관된 미디어 파일 업데이트
    List<BaseMedia> updatedMedias = baseMediaService.updateMedias(command);

    // 게시글과 미디어 파일 저장
    basePostRepository.save(existingIntro);

    // BasePostDto를 반환하여 업데이트된 정보를 반환
    return BasePostDto.builder()
        .basePost(existingIntro)
        .baseMedias(updatedMedias)
        .build();
  }


  // Delete: 특정 Intro 삭제 (소프트 삭제, 미디어 파일은 진짜 삭제)
  @Transactional
  public void deleteIntro(Long basePostId) {
    Intro intro = (Intro) basePostRepository.findByBasePostId(basePostId);

    if (intro == null) {
      throw new CustomException(ErrorCode.POST_NOT_FOUND);
    }

    // 연관된 미디어 파일을 실제로 삭제
    List<BaseMedia> medias = baseMediaRepository.findAllByBasePost_BasePostId(basePostId);
    if (medias != null && !medias.isEmpty()) {
      baseMediaService.deleteMedias(medias);
    }

    // 게시글을 삭제된 것으로 표시
    intro.markAsDeleted();

    // 변경사항을 저장합니다.
    basePostRepository.save(intro);
  }
}
