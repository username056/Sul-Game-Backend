package org.sejong.sulgamewiki.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Comment;
import org.sejong.sulgamewiki.object.CommentCommand;
import org.sejong.sulgamewiki.object.CommentDto;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.constants.ReportType;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.CommentRepository;
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
  private final ReportService reportService;

  public CommentDto createComment(CommentCommand command) {

    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    BasePost basePost = basePostRepository.findById(command.getBasePostId())
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    Comment comment = Comment.builder()
        .content(command.getContent())
        .member(member)
        .basePost(basePost)
        .likeCount(0)
        .reportedCount(0)
        .isEdited(false)
        .build();

    Comment savedComment = commentRepository.save(comment);

    return CommentDto.builder()
        .comment(savedComment)
        .build();
  }

  public void deleteComment(CommentCommand command) {
    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    // 댓글 작성자가 요청한 사용자인지 확인
    if (!comment.getMember().getMemberId().equals(command.getMemberId())) {
      throw new CustomException(ErrorCode.COMMENT_NOT_OWNED_BY_USER);
    }
    commentRepository.deleteById(comment.getCommentId());
  }

  public CommentDto getComment(CommentCommand command) {
    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    return CommentDto.builder()
        .comment(comment)
        .build();
  }

  public CommentDto updateComment(CommentCommand command) {
    Comment comment = commentRepository.findById(command.getCommentId())
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    // 댓글 작성자가 요청한 사용자인지 확인
    if (!comment.getMember().getMemberId().equals(command.getMemberId())) {
      throw new CustomException(ErrorCode.COMMENT_NOT_OWNED_BY_USER);
    }

    comment.setContent(command.getContent());
    comment.setEdited(true);

    Comment updatedComment = commentRepository.save(comment);

    return CommentDto.builder()
        .comment(updatedComment)
        .build();
  }

  public void reportComment(Long commentId, Long memberId, ReportType reportType) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    ReportCommand command = ReportCommand.builder()
        .memberId(memberId)
        .sourceId(commentId)
        .sourceType(SourceType.COMMENT)
        .reportType(reportType)
        .build();

    reportService.reportComment(command);
  }

}
