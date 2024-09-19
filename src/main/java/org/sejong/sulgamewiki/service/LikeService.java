package org.sejong.sulgamewiki.service;


import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.Comment;
import org.sejong.sulgamewiki.object.CommentCommand;
import org.sejong.sulgamewiki.object.CommentDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.object.constants.ExpRule;
import org.sejong.sulgamewiki.object.constants.ScoreRule;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.CommentRepository;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final CommentRepository commentRepository;
  private final ExpManagerService expManagerService;
  private final MemberInteractionRepository memberInteractionRepository;

  //TODO comment Like 추가해야함
  //FIXME: 지금 basePost에 LikedMemberIds 추가함 : 이전 코드 수정 필요

  public BasePostDto likePost(BasePostCommand command) {
    if (!command.getIsLiked()) {
      return upPostLike(command);
    } else{
      return cancelPostLike(command);
    }
  }

  public BasePostDto upPostLike(BasePostCommand command) {

    // 좋아요 할 게시물
    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 게시글 작성자
    Member postOwner = memberRepository.findById(basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 좋아요 누른 멤버
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    // 게시글 좋아요
    basePost.upLike(command.getMemberId());

    // 좋아요한 포스트 목록에 추가
    interaction.addLikedPostId(command);

    // 게시글 Score 갱신
    basePost.updateScore(ScoreRule.UP_LIKE);

    // 게시글 작성자 EXP 갱신
    expManagerService.updateExp(postOwner, ExpRule.POST_LIKE_GIVEN);
    // 좋아요 누른 멤버 EXP 갱신
    expManagerService.updateExp(member, ExpRule.POST_LIKE_GIVE);

    // 변경사항 저장
    memberInteractionRepository.save(interaction);
    BasePost savedBasePost = basePostRepository.save(basePost);

    return BasePostDto.builder()
        .basePost(savedBasePost)
        .isLiked(true)
        .build();
  }

  public BasePostDto cancelPostLike(BasePostCommand command) {

    // 좋아요 취소할 게시물
    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 게시글 작성자
    Member postOwner = memberRepository.findById(basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 좋아요 취소하는 멤버
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    // 게시글 좋아요 취소
    basePost.cancelLike(command.getMemberId());

    // 좋아요 목록에서 삭제
    interaction.removeLikedPostId(command);

    // 게시글 점수 갱신
    basePost.updateScore(ScoreRule.DOWN_LIKE);

    // 게시글 작성자 EXP 갱신
    expManagerService.updateExp(postOwner, ExpRule.POST_LIKE_CANCELED);
    // 즐겨찾기 누른 멤버 EXP 갱신
    expManagerService.updateExp(member, ExpRule.POST_LIKE_CANCEL);

    // 변경사항 저장
    memberInteractionRepository.save(interaction);
    BasePost savedBasePost = basePostRepository.save(basePost);

    return BasePostDto.builder()
        .basePost(savedBasePost)
        .isLiked(false)
        .build();
  }



  public CommentDto likeComment(CommentCommand command) {
    if (!command.getIsLiked()) {
      return upCommentLike(command);
    } else{
      return cancelCommentLike(command);
    }
  }
  public CommentDto upCommentLike(CommentCommand command) {

    // 댓글이 있는 게시물
    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 좋아요 할 댓글
    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    // 댓글 작성자
    Member commentOwner = memberRepository.findById(comment.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 좋아요 할 멤버
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    // 멤버가 누른 총 댓글 좋아요 개수 갱신
    interaction.increaseCommentLikeCount();

    // 댓글 좋아요
    comment.upLike(command.getMemberId());

    // 댓글 작성자 EXP 갱신
    expManagerService.updateExp(commentOwner, ExpRule.COMMENT_LIKE_GIVEN);

    // 변경사항 저장
    memberInteractionRepository.save(interaction);
    basePostRepository.save(basePost);

    return CommentDto.builder()
        .comment(comment)
        .isLiked(true)
        .build();
  }

  public CommentDto cancelCommentLike(CommentCommand command) {

    // 댓글이 있는 게시물
    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 좋아요 할 댓글
    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    // 댓글 작성자
    Member commentOwner = memberRepository.findById(comment.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 좋아요 할 멤버
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    // 멤버가 누른 총 댓글 좋아요 개수 갱신
    interaction.decreaseCommentLikeCount();

    // 댓글 좋아요 취소
    comment.cancelLike(command.getMemberId());

    // 댓글 작성자 EXP 갱신
    expManagerService.updateExp(commentOwner, ExpRule.COMMENT_LIKE_CANCELED);

    // 변경사항 저장
    memberInteractionRepository.save(interaction);
    basePostRepository.save(basePost);

    return CommentDto.builder()
        .comment(comment)
        .isLiked(false)
        .build();
  }
}
