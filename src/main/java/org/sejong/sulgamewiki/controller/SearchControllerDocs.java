package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.SearchCommand;
import org.sejong.sulgamewiki.object.SearchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface SearchControllerDocs {

  @Operation(
      summary = "전체 게시물 검색",
      description = """
          **전체 게시물 검색**

          이 API는 모든 게시물의 `title`, `introduction`, `description` 필드에서 입력된 검색어를 포함하는 게시물을 검색합니다.
          검색 결과는 게시물 타입별(`Intro`, `OfficialGame`, `CreationGame`)로 분류되어 반환됩니다.

          **JWT 토큰 필요:**
          
          이 API는 인증이 필요하지 않습니다.

          **입력 파라미터 값:**

          - **`String query`**: 검색할 문자열입니다. 게시물의 `title`, `introduction`, `description` 필드에서 해당 문자열을 포함하는 게시물을 검색합니다.

            예시:
            - `"술게임"`
            - `"재미있는 게임"`

          **반환 파라미터 값:**

          - **`List<Intro> intros`**: 검색어와 일치하는 `Intro` 게시물 리스트.
          - **`List<OfficialGame> officialGames`**: 검색어와 일치하는 `OfficialGame` 게시물 리스트.
          - **`List<CreationGame> creationGames`**: 검색어와 일치하는 `CreationGame` 게시물 리스트.

          각 리스트는 검색 결과에 따라 빈 리스트일 수 있습니다.

          **예외 상황:**

          - 검색어가 빈 문자열이거나 `null`인 경우, 모든 게시물을 반환합니다.
          """
  )
  ResponseEntity<SearchDto> searchAll(
      @ModelAttribute SearchCommand command);
}