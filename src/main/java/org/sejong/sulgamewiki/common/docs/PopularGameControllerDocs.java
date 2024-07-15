package org.sejong.sulgamewiki.common.docs;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sejong.sulgamewiki.game.popular.dto.request.CreatePopularGameRequest;
import org.sejong.sulgamewiki.game.popular.dto.response.CreatePopularGameResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "인기 게임 API", description = "인기 게임 관련 API")
public interface PopularGameControllerDocs {


  @Operation(
      summary = "인기 게임 생성",
      description = "새로운 인기 게임을 생성합니다.",
      requestBody = @RequestBody(
          content = @Content(
              mediaType = "multipart/form-data",
              schema = @Schema(implementation = CreatePopularGameRequest.class)
          )
      )
  )
  ResponseEntity<CreatePopularGameResponse> createPopularGame(
      @Parameter(description = "회원 ID", required = true, example = "1")
      @RequestParam Long memberId,

      @Parameter(description = "게임 생성 요청 데이터", required = true, content = @Content(mediaType = "application/json"))
      CreatePopularGameRequest request
  );

}
