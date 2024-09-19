package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.HomeCommand;
import org.sejong.sulgamewiki.object.HomeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface HomeControllerDocs {

  @Operation(
      summary = "메인 페이지 조회",
      description = """
            **메인 페이지 조회**

            메인 페이지의 정보를 조회합니다.

            **입력 파라미터 값:**

            - **`Integer pageNumber`**: 조회할 페이지 번호. 0 넣어서 보내줘(정렬 후 첫번째 페이지를 보여줘야 하니까).
            
            - **`Integer pageSize`**: 페이지당 항목 수. 5~10 정도 하면 될듯?

            **반환 파라미터 값:**

            - **`HomeDto`**: 조회된 메인 페이지의 정보를 담고 있습니다.
              
              - **`latestCreationGames`**: 최신 창작 게임 리스트
              - **`latestIntros`**: 최신 인트로 리스트
              - **`officialGamesByLikes`**: 좋아요 기준으로 정렬된 공식 게임 리스트
              - **`creationGamesByDailyScore`**: 일간 점수 기준으로 정렬된 창작 게임 리스트
              - **`introsByDailyScore`**: 일간 점수 기준으로 정렬된 인트로 리스트
              - **`officialGamesByDailyScore`**: 일간 점수 기준으로 정렬된 공식 게임 리스트
              - **`introsByLikes`**: 좋아요 기준으로 정렬된 인트로 리스트
              - **`introsByViews`**: 조회수 기준으로 정렬된 인트로 리스트
              - **`gamesByWeeklyScore`**: 주간 점수 기준으로 정렬된 게임 리스트
              - **`hasNext`**: 다음 페이지 존재 여부
            """
  )
  ResponseEntity<HomeDto> getHomepage(@ModelAttribute HomeCommand homeCommand);

}
