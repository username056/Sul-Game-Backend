package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.CommentCommand;
import org.sejong.sulgamewiki.object.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface CommentControllerDocs {

  @Operation(
      summary = "댓글 생성",
      description = """
            **토큰 필요**
            
            **댓글 생성**
    
            새로운 댓글을 생성합니다.
    
            **입력 파라미터 값:**
                    
            - **`Long BasePostId;`**: 댓글을 달 게시물의 Id값
                    
            - **`String content`**: 댓글의 내용
              _최대 00자_
             
            **반환 파라미터 값:**
    
            - **`CommentDto command`**: 생성된 댓글과 관련된 정보
            """
  )
  ResponseEntity<CommentDto> createComment(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute CommentCommand command);

  @Operation(
      summary = "댓글 수정",
      description = """
            **토큰 필요**
            
            **댓글 수정**

            기존의 댓글을 수정합니다.
    
            **입력 파라미터 값:**
                    
            - **`Long commentId`**: 수정할 댓글의 Id값
                    
            - **`String content`**: 수정할 댓글의 내용
              _최대 00자_
             
            **반환 파라미터 값:**
    
            - **`CommentDto command`**: 수정된 댓글과 관련된 정보
            """
  )
  ResponseEntity<CommentDto> updateComment(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute CommentCommand command);

  @Operation(
      summary = "댓글 삭제",
      description = """
            **토큰 필요**
            
            **댓글 삭제**

            특정 댓글을 삭제합니다.
    
            **입력 파라미터 값:**
                    
            - **`Long commentId`**: 삭제할 댓글의 Id값
            
            - **`Long BasePostId;`**: 삭제할 댓글이 있는 게시물의 Id값
             
            **반환 파라미터 값:**
    
            - **`void`**: 삭제가 완료되면 아무 값도 반환하지 않습니다.
            """
  )
  ResponseEntity<Void> deleteComment(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute CommentCommand command);

  @Operation(
      summary = "댓글 좋아요",
      description = """
            **토큰 필요**
            
            **댓글 좋아요**

            특정 댓글을 좋아요합니다.
    
            **입력 파라미터 값:**
                    
            - **`Long commentId`**: 좋아요할 댓글의 Id값
            
            - **`Long BasePostId;`**: 좋아요할 댓글이 있는 게시물의 Id값
             
            **반환 파라미터 값:**
    
            - **`Comment comment`**: 좋아요가 완료되면 해당 댓글의 정보를 반환합니다.
            """
  )
  ResponseEntity<CommentDto> likeComment(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute CommentCommand command);
}
