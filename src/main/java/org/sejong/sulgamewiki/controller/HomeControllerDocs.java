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
  ResponseEntity<HomeDto> getHomepage(@ModelAttribute HomeCommand command);

  @Operation(
      summary = "게시물 전체보기 조회",
      description = """
            **게시물 전체보기 조회**

            메인 페이지의 특정 섹션에 대한 게시물 전체목록을 조회합니다.

            **입력 파라미터 값:**

            - **`Integer pageNumber`**: 조회할 페이지 번호. 기본값은 0입니다.(디폴트라는 말 아님 값 넣으셈)

            - **`Integer pageSize`**: 페이지당 항목 수. 기본값은 10입니다.(디폴트라는 말 아님 값 넣으셈)

            - **`SourceType postType`**: 게시물 유형 (예: CREATION, INTRO, OFFICIAL).

            - **`SortBy sortBy`**: 정렬 기준 (예: CREATED_DATE, LIKES, VIEWS).

            - **`Direction direction`**: 정렬 방향 (ASC: 오름차순, DESC: 내림차순).

            **반환 파라미터 값:**

            - **`HomeDto`**: 조회된 섹션의 게시물 정보를 담고 있습니다.
            """
  )
  ResponseEntity<HomeDto> getPosts(@ModelAttribute HomeCommand command);

}
