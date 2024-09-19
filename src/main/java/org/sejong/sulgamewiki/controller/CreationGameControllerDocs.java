package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.sejong.sulgamewiki.object.HomeCommand;
import org.sejong.sulgamewiki.object.HomeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface CreationGameControllerDocs {

  @Operation(
      summary = "창작 게임 생성",
      description = """
          **토큰 필요**
            
          **창작 게임 생성**

          새로운 창작 게임 게시물을 생성합니다.

          **입력 파라미터 값:**
                  
          - **`String title`**: 게임의 제목
            _최대 20자_
            
          - **`String introduction`**: 게임의 소개 텍스트
            _최대 20자_
            
          - **`String description`**: 게임에 대한 상세 설명
            _최대 1000자_
            
          - **`String introLyrics`**: 게임의 인트로의 텍스트
            _최대 150자_
            
          - **`MultipartFile introMedia`**: 게임의 인트로의 미디어 파일
            _선택 사항_
            
          - **`List<MultipartFile> multipartFiles`**: 게임과 관련된 미디어 파일들
            _선택 사항_
            
          - **`Object<Icon>`**: 게임에 대한 아이콘
            _선택 사항_
            
          - **`List<String> tags`**: 게임에 대한 태그
            _선택 사항_
            
          - **`Boolean IsCreatorInfoPrivate`**: 작성자 정보 공개 여부
            _선택 사항_ 기본값이 공개
            
                    
          **반환 파라미터 값:**

          - **`CreationGame creationGame`**: 생성된 창작 게임 게시물
          - **`List<BaseMedia> baseMedias`**: 게임과 연관된 미디어 파일 리스트
          - **`BaseMedia introMediaInGame`**: 게임 인트로 미디어 파일
          """
  )
  ResponseEntity<BasePostDto> createCreationGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "창작 게임 조회",
      description = """
          **창작 게임 조회**

          ID를 사용하여 특정 창작 게임 게시물을 조회합니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 조회할 게시물의 고유 ID

          **반환 파라미터 값:**

          - **`CreationGame creationGame`**: 조회된 창작 게임 게시물
          - **`List<BaseMedia> baseMedias`**: 게임과 연관된 미디어 파일 리스트
          - **`BaseMedia introMediaInGame`**: 게임 인트로 미디어 파일
          """
  )
  ResponseEntity<BasePostDto> getCreationGame(
      @ModelAttribute BasePostCommand command);

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
  public ResponseEntity<HomeDto> getSortedCreationGames(
      @ModelAttribute HomeCommand command);

  @Operation(
      summary = "창작 게임 수정",
      description = """
          **토큰 필요**
           
          **창작 게임 수정**

           기존 창작 게임 게시물을 수정합니다.

          **입력 파라미터 값**
                  
          - **`String title`**: 게임의 제목
             _최대 20자_
             
          - **`String introduction`**: 게임의 소개 텍스트
             _최대 20자_
             
          - **`String description`**: 게임에 대한 상세 설명
             _최대 1000자_
             
          - **`String introLyrics`**: 게임의 인트로의 텍스트
             _선택사항_ _최대 150자_
             
          - **`MultipartFile introMedia`**: 게임의 인트로의 미디어 파일
             _선택 사항_
             
          - **`List<MultipartFile> multipartFiles`**: 게임과 관련된 미디어 파일들
             _선택 사항_
             
          - **`Object<Icon>`**: 게임에 대한 아이콘
             _선택 사항_
             
          - **`List<String> tags`**: 게임에 대한 태그
             _선택 사항_
             
          - **`Boolean IsCreatorInfoPrivate`**: 작성자 정보 공개 여부
             _선택 사항_ 기본값이 공개
             
           **반환 파라미터 값:**

           - **`CreationGame creationGame`**: 생성된 창작 게임 게시물
           - **`List<BaseMedia> baseMedias`**: 게임과 연관된 미디어 파일 리스트
           - **`BaseMedia introMediaInGame`**: 게임 인트로 미디어 파일
           """
  )
  ResponseEntity<BasePostDto> updateCreationGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "게임 삭제",
      description = """
          **토큰 필요**
                    
          **게임 삭제**

          특정 게임을 소프트 삭제합니다. 관련 미디어 파일은 삭제됩니다.
          삭제된 게임은 더이상 활성 상태로 조회되지 않지만, 데이터베이스에서는 제거되지 않습니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 삭제할 게임의 고유 ID

          **반환 파라미터 값:**

          - 없음
          """
  )
  ResponseEntity<Void> deleteCreationGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "창작게임 좋아요",
      description = """
          **토큰 필요**
                    
          **창작게임 좋아요**

          특정 창작게임 좋아요합니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 좋아요할 창작게임 고유 ID
                    
          - **`Boolean isLiked`**: 게시물에 대한 사용자의 좋아요 상태

          **반환 파라미터 값:**

          - **`BasePost basePost`**: 해당 게시물의 정보를 반환합니다
          """
  )
  ResponseEntity<BasePostDto> likeCreation(
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
  )
  public ResponseEntity<BasePostDto> bookmarkCreation(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command
  );
}
