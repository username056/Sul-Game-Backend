package org.sejong.sulgamewiki.game.popular.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.log.LogMonitoringInvocation;
import org.sejong.sulgamewiki.common.utils.annotations.FilesParameter;
import org.sejong.sulgamewiki.game.popular.application.PopularGameService;
import org.sejong.sulgamewiki.game.popular.dto.request.CreatePopularGameRequest;
import org.sejong.sulgamewiki.game.popular.dto.response.CreatePopularGameResponse;
import org.sejong.sulgamewiki.game.popular.dto.response.GetPopularGameResponse;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/popular-game")
@RequiredArgsConstructor
public class PopularGameController {

  private final PopularGameService popularGameService;

  @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(description = "인기게임 생성")
  @LogMonitoringInvocation
  public ResponseEntity<CreatePopularGameResponse> createPopularGame(
      @Parameter(description = "MemberId", required = true, example = "1")
      @RequestParam Long memberId,

      @Parameter(description = "인기게임 제목", required = true, example = "인기게임 제목을 적어주세요.")
      @RequestParam String title,

      @Parameter(description = "인기게임 설명", required = true, example = "어떻게 하는 게임인지 설명해주세요.")
      @RequestParam String description,

      @Parameter(description = "인기게임 간략한 한줄소개", required = true, example = "간략한 한줄소개를 적어주세요")
      @RequestParam String introduction,

      @Parameter(description = "파일 목록", required = true)
      @RequestParam("multipartFiles") List<MultipartFile> multipartFiles
  ) {
    // CreatePopularGameRequest 객체를 Builder 패턴을 사용하여 생성
    CreatePopularGameRequest request = CreatePopularGameRequest.of(title,
        description, introduction);

    // 서비스 호출하여 인기게임 생성
    CreatePopularGameResponse popularGame
        = popularGameService.createPopularGame(memberId, request,
        multipartFiles);
    return ResponseEntity.ok(popularGame);
  }

  @GetMapping("/{id}")
  @LogMonitoringInvocation
  public ResponseEntity<GetPopularGameResponse> getPopularGame(
      @PathVariable Long id) {
    GetPopularGameResponse response = popularGameService.getPopularGame(id);
    return ResponseEntity.ok(response);
  }

//  @GetMapping("")
//  @Operation(description = "인기게임 리스트")
//  public ResponseEntity<List<GetPopularGamesResponse>> getPopularGames() {
//    List<GetPopularGamesResponse> response = popularGameService.getPopularGames();
//    return ResponseEntity.ok(response);
//  }

//  @GetMapping("/update/{id}")
//  public ResponseEntity<UpdatePopularGameResponse> updatePopularGame(
//      @PathVariable Long popularGameId,
//      @AuthenticationPrincipal CustomUserDetails customUserDetails,
//      @RequestBody UpdatePopularGameRequest request
//  ) {
//    UpdatePopularGameResponse response = popularGameService.updatePopularGame(
//        popularGameId,
//        customUserDetails.getMember().getId(),
//        request);
//    return ResponseEntity.ok(response);
//  }


}
