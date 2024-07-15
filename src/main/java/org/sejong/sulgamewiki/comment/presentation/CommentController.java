package org.sejong.sulgamewiki.comment.presentation;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.sejong.sulgamewiki.comment.application.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        return ResponseEntity.noContent().build();
    }
}
