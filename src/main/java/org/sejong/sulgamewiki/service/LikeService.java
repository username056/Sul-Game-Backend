package org.sejong.sulgamewiki.service;


import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.Comment;
import org.sejong.sulgamewiki.object.CommentCommand;
import org.sejong.sulgamewiki.object.CommentDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.constants.ExpRule;
import org.sejong.sulgamewiki.object.constants.ScoreRule;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.CommentRepository;
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

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 게시글 작성자
    Member postOwner = memberRepository.findById(basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 좋아요 누른 멤버
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    basePost.upLike(command.getMemberId());

    expManagerService.updateExp(postOwner, ExpRule.POST_LIKE_GIVEN);
    expManagerService.updateExp(member, ExpRule.POST_LIKE_GIVE);
    // 게시글 Score
    basePost.updateScore(ScoreRule.UP_LIKE);

    BasePost savedBasePost = basePostRepository.save(basePost);

    return BasePostDto.builder()
        .basePost(savedBasePost)
        .isLiked(true)
        .build();
  }

  public BasePostDto cancelPostLike(BasePostCommand command) {

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 게시글 작성자
    Member postOwner = memberRepository.findById(basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 좋아요 누른 멤버
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    basePost.cancelLike(command.getMemberId());

    expManagerService.updateExp(postOwner, ExpRule.POST_LIKE_CANCELED);
    expManagerService.updateExp(member, ExpRule.POST_LIKE_CANCEL);

    // 게시글 Score
    basePost.updateScore(ScoreRule.DOWN_LIKE);

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

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    //게시글 작성자
    Member postOwner = memberRepository.findById(basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    comment.upLike(command.getMemberId());

    expManagerService.updateExp(postOwner, ExpRule.COMMENT_LIKE_RECEIVED);
    basePostRepository.save(basePost);

    return CommentDto.builder()
        .comment(comment)
        .isLiked(true)
        .build();
  }

  public CommentDto cancelCommentLike(CommentCommand command) {

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    //게시글 작성자
    Member postOwner = memberRepository.findById(basePost.getMember().getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    comment.cancelLike(command.getMemberId());

    expManagerService.updateExp(postOwner, ExpRule.COMMENT_LIKE_CANCELED);
    basePostRepository.save(basePost);

    return CommentDto.builder()
        .comment(comment)
        .isLiked(false)
        .build();
  }
}
