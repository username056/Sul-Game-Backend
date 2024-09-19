package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.object.constants.ExpRule;
import org.sejong.sulgamewiki.object.constants.ScoreRule;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final ExpManagerService expManagerService;
  private final MemberInteractionRepository memberInteractionRepository;

  public BasePostDto bookmarkPost(BasePostCommand command) {
    if (!command.getIsBookmarked()) {
      return doBookmark(command);
    } else{
      return undoBookmark(command);
    }
  }

  public BasePostDto doBookmark(BasePostCommand command){

    // 즐겨찾기 할 게시물
    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 게시글 작성자
    Member postOwner = memberRepository.findById(basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 즐겨찾기 할 멤버
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    // 즐겨찾기에 추가
    interaction.addBookmarkedPostId(command);

    // 게시글 Score 갱신
    basePost.updateScore(ScoreRule.BOOKMARK);

    // 게시글 작성자 EXP 갱신
    expManagerService.updateExp(postOwner, ExpRule.BOOKMARKED);
    // 즐겨찾기 누른 멤버 EXP 갱신
    expManagerService.updateExp(member, ExpRule.BOOKMARK);

    // 변경사항 저장
    memberInteractionRepository.save(interaction);
    BasePost savedBasePost = basePostRepository.save(basePost);

    return BasePostDto.builder()
        .basePost(savedBasePost)
        .isBookmarked(true)
        .build();
  }

  public BasePostDto undoBookmark(BasePostCommand command){

    // 즐겨찾기 취소할 게시물
    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 게시글 작성자
    Member postOwner = memberRepository.findById(basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 즐겨찾기 취소하는 멤버
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    // 즐겨찾기에서 삭제
    interaction.removeBookmarkedPostId(command);

    // 게시글 점수 갱신
    basePost.updateScore(ScoreRule.CANCEL_BOOKMARK);

    // 게시글 작성자 EXP 갱신
    expManagerService.updateExp(postOwner, ExpRule.BOOKMARKED_CANCEL);
    // 즐겨찾기 누른 멤버 EXP 갱신
    expManagerService.updateExp(member, ExpRule.BOOKMARK_CANCEL);

    // 변경사항 저장
    memberInteractionRepository.save(interaction);
    BasePost savedBasePost = basePostRepository.save(basePost);

    return BasePostDto.builder()
        .basePost(savedBasePost)
        .isBookmarked(false)
        .build();
  }
}
