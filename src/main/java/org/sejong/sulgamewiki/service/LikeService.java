package org.sejong.sulgamewiki.service;


import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;

  //TODO comment Like 추가해야함
  //FIXME: 지금 basePost에 LikedMemberIds 추가함 : 이전 코드 수정 필요

  public BasePostDto upPostLike(BasePostCommand command) {
    BasePostDto dto = BasePostDto.builder().build();

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    basePost.upLike(command.getMemberId());

    BasePost savedBasePost = basePostRepository.save(basePost);

    dto.setBasePost(savedBasePost);
    return dto;
  }

  public BasePostDto downPostLike(BasePostCommand command) {
    BasePostDto dto = BasePostDto.builder().build();

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 좋아요 취소
    basePost.cancelLike(command.getMemberId());

    BasePost savedBasePost = basePostRepository.save(basePost);

    dto.setBasePost(savedBasePost);
    return dto;
  }
}
