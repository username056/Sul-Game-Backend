package org.sejong.sulgamewiki.common.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.sejong.sulgamewiki.intro.dto.response.CreateIntroResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "소개 노래 API", description = "술게임 시작 노래 관련 API")
public interface IntroControllerDocs {

  @Operation(
      summary = "소개 노래 생성",
      description = "새로운 소개 노래를 생성합니다.",
      requestBody = @RequestBody(
          content = @Content(
              mediaType = "multipart/form-data",
              schema = @Schema(implementation = CreateIntroRequest.class)
          )
      )
  )
  ResponseEntity<CreateIntroResponse> createIntro(
      @Parameter(description = "회원 ID", required = true, example = "1")
      @RequestParam Long memberId,

      @Parameter(description = "노래 생성 요청 데이터", required = true)
      @ModelAttribute CreateIntroRequest request,

      @Parameter(description = "파일 목록", required = true)
      @RequestParam("files") List<MultipartFile> files


  );
}
