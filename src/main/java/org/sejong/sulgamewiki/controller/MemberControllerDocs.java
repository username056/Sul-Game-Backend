package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface MemberControllerDocs {

  @Operation(
      summary = "회원 등록 완료",
      description = """
        **회원 등록 완료**

        소셜로그인 이후 나머지 회원가입을 완료합니다.

        **입력 파라미터 값:**

        - **`String nickname`**: 회원의 닉네임 (예: "서새찬")
          _최대 20자, 특수문자 사용 불가_

        - **`LocalDate birthDate`**: 회원의 생년월일 (예: "1999-10-29")  
          _형식: YYYY-MM-DD_

        - **`String college`**: 회원의 대학 이름 (예: "세종대학교")  
          _최대 50자_

        - **`Boolean isUniversityPublic`**: 대학 공개 여부 (예: "true" or "false")  
          `기본값: true`

        - **`Boolean isNotificationEnabled`**: 알림 수신 여부 (예: "true" or "false")  
          `기본값: true`

        **반환 파라미터 값:**

        - **`Member member`**: 회원가입이 완료된 회원
        """
  )
  ResponseEntity<MemberDto> completeRegistration(@ModelAttribute MemberCommand command);

  @Operation(
      summary = "마이페이지",
      description = """
        **마이페이지**

        회원의 마이페이지 정보를 제공합니다.

        **입력 파라미터 값:**

        - **`Long memberId`**: 회원의 고유 ID

        **반환 파라미터 값:**

        - **`Member member`**: 회원 정보
        - **`MemberContentInteraction memberContentInteraction`**: 회원의 컨텐츠 상호작용 정보
        """
  )
  ResponseEntity<MemberDto> getProfile(@ModelAttribute MemberCommand command);

  @Operation(
      summary = "좋아요한 글",
      description = """
        **좋아요한 글**

        회원이 좋아요한 글을 제공합니다.

        **입력 파라미터 값:**

        - **`Long memberId`**: 회원의 고유 ID

        **반환 파라미터 값:**

        - **`List<BasePost> likedOfficialGames`**: 좋아요한 공식 게임 게시글 리스트
        - **`List<BasePost> likedCreationGame`**: 좋아요한 창작 게임 게시글 리스트
        - **`List<BasePost> likedIntros`**: 좋아요한 소개글 리스트
        """
  )
  ResponseEntity<MemberDto> getLikedPosts(@ModelAttribute MemberCommand command);

  @Operation(
      summary = "즐겨찾기한 글",
      description = """
        **즐겨찾기한 글**

        회원이 즐겨찾기한 글을 제공합니다.

        **입력 파라미터 값:**

        - **`Long memberId`**: 회원의 고유 ID

        **반환 파라미터 값:**

        - **`List<BasePost> bookmarkedOfficialGameIds`**: 즐겨찾기한 공식 게임 게시글 리스트
        - **`List<BasePost> bookmarkedCreationGameIds`**: 즐겨찾기한 창작 게임 게시글 리스트
        - **`List<BasePost> bookmarkedIntroIds`**: 즐겨찾기한 소개글 리스트
        """
  )
  ResponseEntity<MemberDto> getBookmarkedPosts(@ModelAttribute MemberCommand command);

  @Operation(
      summary = "회원 프로필 이미지 업데이트",
      description = """
        **회원 프로필 이미지 업데이트**

        회원의 프로필 이미지를 업데이트합니다.

        **입력 파라미터 값:**

        - **`Long memberId`**: 회원의 고유 ID
        - **`MultipartFile multipartFile`**: 업로드할 프로필 이미지 파일

        **반환 파라미터 값:**

        - **`Member member`**: 업데이트된 프로필 이미지를 포함한 회원 정보
        """
  )
  ResponseEntity<MemberDto> updateMemberProfileImage(@ModelAttribute MemberCommand command);

}