package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

// TODO: 명세 주석 다 바꾸기

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
            _최대 100자_

          - **`String lyrics`**: 인트로에 포함될 가사  
            _선택 사항, 최대 2000자_
            
          - **`String description`**: 인트로에 대한 설명  
            _선택 사항, 최대 2000자_

          - **`List<MultipartFile> multipartFiles`**: 인트로와 함께 업로드할 미디어 파일들  
            _선택 사항_
                  
          - **`Object<Icon>`**: 인트로에 대한 아이콘 
           _선택 사항_
           
          - **`List<String> tags`**: 인트로에 대한 태그  
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
      summary = "인트로 수정",
      description = """
          **토큰 필요**
          
          **인트로 수정**

          기존 인트로의 내용을 수정합니다.

          **입력 파라미터 값:**
                  
          - **`Long basePostId`**: 수정할 인트로의 고유 ID
                  
          - **`String title`**: 인트로의 새로운 제목  
            _최대 100자_

          - **`String lyrics`**: 인트로에 포함될 새로운 가사  
            _선택 사항, 최대 2000자_
            
          - **`String description`**: 인트로에 대한 새로운 설명  
            _선택 사항, 최대 2000자_

          - **`List<MultipartFile> multipartFiles`**: 인트로와 함께 업로드할 새로운 미디어 파일들  
            _선택 사항_
                  
          - **`Object<Icon>`**: 인트로에 대한 아이콘 
           _선택 사항_
             
          - **`List<String> tags`**: 인트로에 대한 태그  
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
          단, 미디어 파일은 실제로 삭제됩니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 삭제할 인트로의 고유 ID

          **반환 파라미터 값:**

          - 없음
          """
  )
  ResponseEntity<Void> deleteIntro(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute BasePostCommand command);

}
