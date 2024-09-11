package org.sejong.sulgamewiki.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Comment;
import org.sejong.sulgamewiki.object.CommentCommand;
import org.sejong.sulgamewiki.object.CommentDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.object.constants.ExpRule;
import org.sejong.sulgamewiki.object.constants.ScoreRule;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.CommentRepository;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final MemberRepository memberRepository;
  private final BasePostRepository basePostRepository;
  private final CommentRepository commentRepository;
  private final MemberInteractionRepository memberInteractionRepository;
  private final ExpManagerService expManagerService;

  public CommentDto createComment(CommentCommand command) {

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    Comment comment = Comment.builder()
        .content(command.getContent())
        .member(member)
        .basePost(basePost)
        .likeCount(0)
        .reportedCount(0)
        .build();

    Comment savedComment = commentRepository.save(comment);

    interaction.increaseCommentCount();

    expManagerService.updateExp(member, ExpRule.COMMENT_CREATION);

    basePost.updateScore(ScoreRule.WRITE_COMMENT);
    basePost.increaseCommentCount();

    basePostRepository.save(basePost);

    return CommentDto.builder()
        .comment(savedComment)
        .build();
  }

  public void deleteComment(CommentCommand command) {

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 사용자 Interaction 정보 가져오기
    MemberInteraction interaction = memberInteractionRepository.findById(member.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    // 댓글 작성자가 요청한 사용자인지 확인
    if (!comment.getMember().getMemberId().equals(command.getMemberId())) {
      throw new CustomException(ErrorCode.COMMENT_NOT_OWNED_BY_USER);
    }
    commentRepository.deleteById(comment.getCommentId());

    interaction.decreaseCommentCount();

    expManagerService.updateExp(member, ExpRule.COMMENT_DELETION);

    basePost.updateScore(ScoreRule.DELETE_COMMENT);
    basePost.decreaseCommentCount();
    basePostRepository.save(basePost);
  }

  public CommentDto getComment(CommentCommand command) {
    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    return CommentDto.builder()
        .comment(comment)
        .build();
  }

  public CommentDto updateComment(CommentCommand command) {

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    // 댓글 작성자가 요청한 사용자인지 확인
    if (!comment.getMember().getMemberId().equals(command.getMemberId())) {
      throw new CustomException(ErrorCode.COMMENT_NOT_OWNED_BY_USER);
    }

    comment.setContent(command.getContent());
    comment.markAsUpdated();

    Comment updatedComment = commentRepository.save(comment);

    return CommentDto.builder()
        .comment(updatedComment)
        .build();
  }
}
