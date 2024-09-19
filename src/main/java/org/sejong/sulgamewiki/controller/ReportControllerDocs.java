package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.ReportCommand;
import org.sejong.sulgamewiki.object.ReportDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface ReportControllerDocs {

  @Operation(
      summary = "공식 게임 생성",
      description = """
          **토큰 필요**
            
          게시글을 신고합니다

          **입력 파라미터 값:**
        
          - **`SourceType sourceType`**: 게시글 타입
            
          - **`Long sourceId`**: 게시글 Id
          
          - **`ReportType reportType`**: 신고 사유
            
          
          **반환 파라미터 값:**

          - **`Report report`**: 신고 내용
          """
  )public ResponseEntity<ReportDto> postReport(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute ReportCommand command);

  @Operation(
      summary = "공식 게임 생성",
      description = """
          **토큰 필요**
            
          댓글을 신고합니다

          **입력 파라미터 값:**
        
          - **`SourceType sourceType`**: 게시글 타입
            
          - **`Long sourceId`**: 댓글 Id
          
          - **`ReportType reportType`**: 신고 사유
            
          
          **반환 파라미터 값:**

          - **`Report report`**: 신고 내용
          """
  )public ResponseEntity<ReportDto> commentReport(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute ReportCommand command);
}
