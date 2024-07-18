package org.sejong.sulgamewiki.comment.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.comment.CommentExeption.CommentErrorCode;
import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.sejong.sulgamewiki.comment.domain.entity.CommentType;
import org.sejong.sulgamewiki.comment.domain.repository.CommentRepository;
import org.sejong.sulgamewiki.comment.dto.request.CommentRequest;
import org.sejong.sulgamewiki.comment.dto.response.CommentResponse;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;
import org.sejong.sulgamewiki.game.popular.domain.repository.PopularGameRepository;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.sejong.sulgamewiki.member.domain.repository.MemberRepository;
import org.sejong.sulgamewiki.member.exception.MemberErrorCode;
import org.sejong.sulgamewiki.member.exception.MemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public CommentResponse addComment(CommentRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .typeId(request.getTypeId())
                .commentType(request.getCommentType())
                .member(member)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException(CommentErrorCode.COMMENT_NOT_FOUND.getMessage());
        }
        commentRepository.deleteById(commentId);
    }
}
