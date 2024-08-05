package org.sejong.sulgamewiki.comment.presentation;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.comment.application.CommentService;
import org.sejong.sulgamewiki.comment.dto.request.CommentRequest;
import org.sejong.sulgamewiki.comment.dto.response.CommentResponse;
import org.sejong.sulgamewiki.common.log.LogMonitoringInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping
    @LogMonitoringInvocation
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
        CommentResponse response = commentService.createComment(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commeontId}")
    @LogMonitoringInvocation
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestParam Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.noContent().build();
    }
}
