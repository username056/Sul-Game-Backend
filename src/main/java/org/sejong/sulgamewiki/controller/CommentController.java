package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.CommentCommand;
import org.sejong.sulgamewiki.object.CommentDto;
import org.sejong.sulgamewiki.service.CommentService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(
    name = "댓글 관리 API",
    description = "댓글 관리 API 제공"
)
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    @LogMonitoringInvocation
    public ResponseEntity<CommentDto> createComment(
        @RequestBody CommentCommand command) {
        CommentDto dto = commentService.createComment(command);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("")
    @LogMonitoringInvocation
    public ResponseEntity<Void> deleteComment(
        @RequestBody CommentCommand command
    ) {
        commentService.deleteComment(command);
        return ResponseEntity.noContent().build();
    }
}
