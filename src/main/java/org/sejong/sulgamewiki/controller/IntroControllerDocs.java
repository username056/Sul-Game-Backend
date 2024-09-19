package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.HomeCommand;
import org.sejong.sulgamewiki.object.HomeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

public interface IntroControllerDocs {

  @Operation(
      summary = "인트로 생성",
      description = """
          **토큰 필요**
          
          **인트로 생성**

          새로운 인트로를 생성합니다.

          **입력 파라미터 값:**
                  
          - **`String introType`**: 인트로 종류(opening, gathering)
                  
          - **`String title`**: 인트로의 제목  
            _최대 00자_

          - **`String lyrics`**: 인트로에 포함될 가사  
            _ 최대 00자_
            
          - **`String description`**: 인트로에 대한 설명  
            _ 최대 00자_

          - **`List<MultipartFile> multipartFiles`**: 인트로와 함께 업로드할 미디어 파일들  
            _선택 사항_
                  
          - **`Object<Icon>`**: 인트로에 대한 아이콘 
           _선택 사항_
           
          - **`List<String> tags`**: 인트로에 대한 태그  
           _선택 사항_
           
          - **`Boolean isCreatorInfoPrivate`**: 게시물 작성자 표시 여부
           _선택 사항_
           
          **반환 파라미터 값:**

          - **`BasePostDto basePost`**: 생성된 인트로와 관련된 정보
          """
  )
  ResponseEntity<BasePostDto> createIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "인트로 조회",
      description = """
          **인트로 조회**

          특정 인트로의 세부 정보를 조회합니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 조회할 인트로의 고유 ID

          **반환 파라미터 값:**

          - **`BasePostDto basePost`**: 조회된 인트로와 관련된 정보
          """
  )
  ResponseEntity<BasePostDto> getIntro(@ModelAttribute BasePostCommand command);

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
  )public ResponseEntity<HomeDto> getSortedIntros(
      @ModelAttribute HomeCommand command);

  @Operation(
      summary = "인트로 수정",
      description = """
          **토큰 필요**
          
          **인트로 수정**

          기존 인트로의 내용을 수정합니다.

          **입력 파라미터 값:**
                  
          - **`String introType`**: 인트로 종류(opening, gathering)
            _선택 사항_
                  
          - **`String title`**: 인트로의 제목
            _최대 00자_

          - **`String lyrics`**: 인트로에 포함될 가사  
            _ 최대 00자_
            
          - **`String description`**: 인트로에 대한 설명  
            _ 최대 00자_

          - **`List<MultipartFile> multipartFiles`**: 인트로와 함께 업로드할 미디어 파일들  
            _선택 사항_
                  
          - **`Object<Icon>`**: 인트로에 대한 아이콘 
           _선택 사항_
           
          - **`List<String> tags`**: 인트로에 대한 태그  
           _선택 사항_
           
          - **`Boolean isCreatorInfoPrivate`**: 게시물 작성자 표시 여부
           _선택 사항_

          **반환 파라미터 값:**

          - **`BasePostDto basePost`**: 수정된 인트로와 관련된 정보
          """
  )
  ResponseEntity<BasePostDto> updateIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "인트로 삭제",
      description = """
          **토큰 필요**
          
          **인트로 삭제**

          특정 인트로를 소프트 삭제합니다. 삭제된 인트로는 더 이상 활성 상태로 조회되지 않지만, 데이터베이스에서는 제거되지 않습니다.
          단, 미디어 파일은 실제로 삭제됩니다. (즉, 제목이나 설명 등 문자열 값은 살릴 수 있지만 미디어 파일들은 못 살림)

          **입력 파라미터 값:**

          - **`Long basePostId`**: 삭제할 인트로의 고유 ID

          **반환 파라미터 값:**

          - 없음
          """
  )
  ResponseEntity<Void> deleteIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "인트로 좋아요",
      description = """
          **토큰 필요**
          
          **인트로 좋아요**

          특정 인트로를 좋아요합니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 좋아요할 인트로의 고유 ID
          
          - **`Boolean isLiked`**: 게시물에 대한 사용자의 좋아요 상태

          **반환 파라미터 값:**

          - **`BasePost basePost`**: 해당 게시물의 정보를 반환합니다
          """
  )
  ResponseEntity<BasePostDto> likeIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "즐겨찾기",
      description = """
          **토큰 필요**
                    
          **즐겨찾기**

          특정 게시물을 즐겨찾기합니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 즐겨찾기할 창작게임 고유 ID
                    
          - **`Boolean isBookmarked`**: 게시물에 대한 사용자의 즐겨찾기 상태

          **반환 파라미터 값:**

          - **`BasePost basePost`**: 해당 게시물의 정보를 반환합니다
          """
  )public ResponseEntity<BasePostDto> bookmarkIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command
  );

}
