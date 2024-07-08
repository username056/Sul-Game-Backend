package org.sejong.sulgamewiki.comment.presentation;

import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.sejong.sulgamewiki.comment.application.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games/{gameId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getCommentsByGameId(@PathVariable Long gameId) {
        return commentService.getCommentsByGameId(gameId);
    }

    @PostMapping
    public Comment addComment(@PathVariable Long gameId, @RequestBody Comment comment) {
        return commentService.addComment(gameId, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
