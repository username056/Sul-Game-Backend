package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface IntroControllerDocs {

  @Operation(
      summary = "인트로 생성",
      description = """
        **인트로 생성**

        새로운 인트로를 생성합니다.

        **입력 파라미터 값:**
        
        - **`Long memberId`**: 회원 ID 

        - **`String title`**: 인트로의 제목  
          _최대 100자_

        - **`String Introduction`**: 인트로에 대한 소개 
          _선택 사항, 최대 2000자_
          
        - **`String lyrics`**: 인트로에 포함될 가사  
          _선택 사항, 최대 2000자_

        - **`String description`**: 인트로에 대한 설명  
          _선택 사항, 최대 2000자_

        - **`List<MultipartFile> multipartFiles`**: 인트로와 함께 업로드할 미디어 파일들  
          _선택 사항_

        **반환 파라미터 값:**

        - **`BasePostDto basePost`**: 생성된 인트로와 관련된 정보
        """
  )
  ResponseEntity<BasePostDto> createIntro(@ModelAttribute BasePostCommand command);

}
