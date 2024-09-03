package org.sejong.sulgamewiki.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    OfficialGame officialGame = basePostRepository.findOfficialGameByBasePostId(
        command.getRelatedOfficialGameId());

    if (officialGame == null) {
      throw new CustomException(ErrorCode.GAME_NOT_FOUND); // 예외를 발생시킴
    }

    Intro savedIntro = basePostRepository.save(
        Intro.builder()
            .title(command.getTitle())
            .lyrics(command.getLyrics())
            .description(command.getDescription())
            .thumbnailIcon(command.getThumbnailIcon())
            .introTags(command.getIntroTags())
            .introType(command.getIntroType())
            .creatorInfoIsPrivate(command.getCreatorInfoIsPrivate())
            .officialGame(officialGame)
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
  public BasePostDto getIntro(BasePostCommand command) {

    Intro intro = basePostRepository.findIntroByBasePostId(
            command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    List<BaseMedia> medias = basePostRepository.findMediasByBasePostId(
        command.getBasePostId());

    return BasePostDto.builder()
        .basePost(intro)
        .baseMedias(medias)
        .build();
  }
  @Transactional
  public BasePostDto updateIntro(BasePostCommand command) {
    // 기존 Intro 게시글 조회
    Intro existingIntro = basePostRepository.findIntroByBasePostId(
            command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 요청한 멤버가 게시글의 작성자인지 확인
    Member requestingMember = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    if (!existingIntro.getMember().equals(requestingMember)) {
      // 작성자가 아니거나 권한이 없는 경우 예외 발생
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
    }

    OfficialGame officialGame = basePostRepository.findOfficialGameByBasePostId(
        command.getRelatedOfficialGameId());

    if (officialGame == null) {
      throw new CustomException(ErrorCode.GAME_NOT_FOUND); // 예외를 발생시킴
    }

    // 게시글의 제목, 가사, 설명 등을 업데이트
    existingIntro.setTitle(command.getTitle());
    existingIntro.setLyrics(command.getLyrics());
    existingIntro.setDescription(command.getDescription());
    existingIntro.setIntroType(command.getIntroType());
    existingIntro.setThumbnailIcon(command.getThumbnailIcon());
    existingIntro.setIntroTags(command.getIntroTags());
    existingIntro.setCreatorInfoIsPrivate(command.getCreatorInfoIsPrivate());
    existingIntro.setOfficialGame(officialGame);

    // 업데이트 표시
    existingIntro.markAsUpdated();
    command.setBasePost(existingIntro);
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
  public void deleteIntro(BasePostCommand command) {
    Intro intro = basePostRepository.findIntroByBasePostId(
            command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 요청한 멤버가 게시글의 작성자인지 확인
    Member requestingMember = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    if (!intro.getMember().equals(requestingMember)) {
      // 작성자가 아니거나 권한이 없는 경우 예외 발생
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
    }

    // 연관된 미디어 파일을 실제로 삭제
    List<BaseMedia> medias = baseMediaRepository.findAllByBasePost_BasePostId(
        command.getBasePostId());
    if (medias != null && !medias.isEmpty()) {
      baseMediaService.deleteMedias(medias);
    }
    // 게시글을 삭제된 것으로 표시
    intro.markAsDeleted();
    // 변경사항을 저장합니다.
    basePostRepository.save(intro);
  }
}
