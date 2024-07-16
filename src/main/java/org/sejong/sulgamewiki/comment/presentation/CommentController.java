package org.sejong.sulgamewiki.comment.presentation;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.sejong.sulgamewiki.comment.application.CommentService;
import org.sejong.sulgamewiki.comment.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO request) {
        Comment comment = commentService.addComment(
                request.getContent(),
                request.getTypeId(),
                request.getCommentType(),
                request.getMemberId()
        );
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
