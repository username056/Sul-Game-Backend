package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.BasePostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface OfficialGameControllerDocs {

  @Operation(
      summary = "공식 게임 생성",
      description = """
          **공식 게임 생성**

          새로운 공식 게임 게시물을 생성합니다.

          **입력 파라미터 값:**

          - **`Long memberId`**: 게시물을 생성하는 회원의 고유 ID
          - **`String title`**: 게임의 제목 
            _최대 100자_
          - **`String introduction`**: 게임의 소개 텍스트 
            _최대 90자_
          - **`String description`**: 게임에 대한 상세 설명 
            _최대 500자_
          - **`List<MultipartFile> multipartFiles`**: 게임과 관련된 미디어 파일들 
            _선택 사항_

          **반환 파라미터 값:**

          - **`OfficialGame officialGame`**: 생성된 공식 게임 게시물
          - **`List<BaseMedia> baseMedias`**: 게임과 연관된 미디어 파일 리스트
          """
  )
  ResponseEntity<BasePostDto> createOfficialGame(@ModelAttribute BasePostCommand command);

  @Operation(
      summary = "공식 게임 조회",
      description = """
          **공식 게임 조회**

          ID를 사용하여 특정 공식 게임 게시물을 조회합니다.

          **입력 파라미터 값:**

          - **`Long basePostId`**: 조회할 게시물의 고유 ID

          **반환 파라미터 값:**

          - **`OfficialGame officialGame`**: 조회된 공식 게임 게시물
          """
  )
  ResponseEntity<BasePostDto> getPopularGame(@ModelAttribute BasePostCommand command);

}
