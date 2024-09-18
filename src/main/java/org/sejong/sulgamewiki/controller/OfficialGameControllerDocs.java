package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface OfficialGameControllerDocs {

  @Operation(
      summary = "공식 게임 생성",
      description = """
          **토큰 필요**
            
          **공식 게임 생성**

          새로운 공식 게임 게시물을 생성합니다.

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

          - **`OfficialGame officialGame`**: 생성된 공식 게임 게시물
          - **`List<BaseMedia> baseMedias`**: 게임과 연관된 미디어 파일 리스트
          - **`BaseMedia introMediaInGame`**: 게임 인트로 미디어 파일
          """
  )
  ResponseEntity<BasePostDto> createOfficialGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "공식 게임 조회",
      description = """
          **공식 게임 조회**

          ID를 사용하여 특정 공식 게임 게시물을 조회합니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 조회할 게시물의 고유 ID

          **반환 파라미터 값:**

          - **`OfficialGame officialGame`**: 조회된 공식 게임 게시물
          - **`List<BaseMedia> baseMedias`**: 게임과 연관된 미디어 파일 리스트
          - **`BaseMedia introMediaInGame`**: 게임 인트로 미디어 파일
          - **`List<CreationGame> creationGames`**: 연관된 창작 게임 리스트
          """
  )
  ResponseEntity<BasePostDto> getOfficialGame(@ModelAttribute BasePostCommand command);

  @Operation(
      summary = "공식 게임 전체조회",
      description = """
          **공식 게임 조회**

          공식 게임 게시물 전체를 조회합니다.

          **입력 파라미터 값:**
          
          없음

          **반환 파라미터 값:**

          - **`List<OfficialGame> officialGames`**: 연관된 창작 게임 리스트
          """
  )
  ResponseEntity<BasePostDto> getOfficialGames(@ModelAttribute BasePostCommand command);

  @Operation(
      summary = "공식 게임 수정",
      description = """
         **토큰 필요**
          
         **공식 게임 수정**

          기존 공식 게임 게시물을 수정합니다.

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

          - **`OfficialGame officialGame`**: 생성된 공식 게임 게시물
          - **`List<BaseMedia> baseMedias`**: 게임과 연관된 미디어 파일 리스트
          - **`BaseMedia introMediaInGame`**: 게임 인트로 미디어 파일
          """
  )
  ResponseEntity<BasePostDto> updateOfficialGame(
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
  ResponseEntity<Void> deleteOfficialGame(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

  @Operation(
      summary = "공식게임 좋아요",
      description = """
          **토큰 필요**
          
          **공식게임 좋아요**

          특정 공식게임 좋아요합니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 좋아요할 공식게임 고유 ID

          **반환 파라미터 값:**

          - **`BasePost basePost`**: 해당 게시물의 정보를 반환합니다
          """
  )
  ResponseEntity<BasePostDto> likeOfficial(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

}
