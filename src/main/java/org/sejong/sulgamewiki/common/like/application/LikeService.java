package org.sejong.sulgamewiki.common.like.application;


import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.entity.BasePost;
import org.sejong.sulgamewiki.common.entity.repository.BasePostRepository;
import org.sejong.sulgamewiki.common.exception.GlobalErrorCode;
import org.sejong.sulgamewiki.common.exception.GlobalException;
import org.sejong.sulgamewiki.common.like.domain.entity.LikedMember;
import org.sejong.sulgamewiki.common.like.domain.repository.LikedMemberRepository;
import org.sejong.sulgamewiki.common.like.exception.LikeErrorCode;
import org.sejong.sulgamewiki.common.like.dto.UpdateLikeResponse;
import org.sejong.sulgamewiki.common.like.exception.LikeException;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final LikedMemberRepository likedMemberRepository;

  public UpdateLikeResponse upLike(Long postId, Long memberId) {
    // 게임 아이디 통해 게임 찾기
    BasePost basePost = basePostRepository.findById(postId)
        .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

    // 멤버 아이디를 통해 멤버 찾기(알림 보낼 때 필요함)
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

    // game 좋아요 +1
    basePost.upLike();

    // game db에 저장
    basePostRepository.save(basePost);

    // 엔티티 생성
    LikedMember likedMember = LikedMember.builder()
        .member(member)
        .basePost(basePost)
        .build();

    likedMemberRepository.save(likedMember);

    // dto 생성, 반환
    return UpdateLikeResponse.builder()
        .postId(postId)
        .likeCount(basePost.getLikes())
        .build();
  }

  public UpdateLikeResponse downLike(Long postId, Long memberId) {
    BasePost basePost = basePostRepository.findById(postId)
        .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    LikedMember likedMember = likedMemberRepository.findByBasePost(basePost)
        .orElseThrow(() -> new LikeException(LikeErrorCode.LIKE_CANNOT_BE_UNDER_ZERO));

    likedMemberRepository.delete(likedMember);
    basePost.cancelLike();
    basePostRepository.save(basePost);

    return UpdateLikeResponse.builder()
        .postId(basePost.getId())
        .likeCount(basePost.getLikes())
        .build();

  }
}
