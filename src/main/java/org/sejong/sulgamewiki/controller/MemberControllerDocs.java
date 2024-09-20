package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.object.CustomUserDetails;
import org.sejong.sulgamewiki.util.annotation.ApiChangeLog;
import org.sejong.sulgamewiki.util.annotation.ApiChangeLogs;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface MemberControllerDocs {

  @ApiChangeLogs({
      @ApiChangeLog(
          date = "2024.09.17",
          author = "서새찬",
          description = "반환 파라미터 값을 `List<BasePost>`에서 `List<CreationGame>`으로 수정"
      ),
      @ApiChangeLog(
          date = "2024.08.25",
          author = "홍길동",
          description = "입력 파라미터 설명 추가"
      ),
      @ApiChangeLog(
          date = "2024.08.10",
          author = "이영희",
          description = "API 최초 작성"
      )
  })
  @Operation(
      summary = "회원 등록 완료",
      description = """
        **회원 등록 완료**

        소셜로그인 이후 나머지 회원가입을 완료합니다. 이 API는 사용자가 소셜 로그인 후 추가 정보를 제공하여 회원가입을 완료할 수 있게 합니다.

        **JWT 토큰 필요:**
        
        이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

        **입력 파라미터 값:**

        - **`String nickname`**: 회원의 닉네임 (예: "서새찬")
          _최대 20자_ 

        - **`LocalDate birthDate`**: 회원의 생년월일 (예: "1999-10-29")  
          _형식: YYYY-MM-DD_

        - **`String university`**: 회원의 대학 이름 (예: "세종대학교")  
          _최대 50자_

        **반환 파라미터 값:**

        - **`Member member`**: 회원가입이 완료된 회원 정보
        """
  )
  ResponseEntity<MemberDto> completeRegistration(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "마이페이지",
      description = """
      **마이페이지**

      이 API는 인증된 사용자의 프로필 정보를 조회합니다. 
      반환되는 데이터는 회원의 기본 정보, 콘텐츠 상호작용 정보, 그리고 회원의 경험치 순위와 퍼센트 등입니다.

      **JWT 토큰 필요:**
      
      이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

      **경험치 관련 반환 값:**
      - **`exp`**: 현재 사용자의 총 경험치.
      - **`expRank`**: 회원의 경험치 순위 (1등이 가장 높음).
      - **`expRankPercentile`**: 회원이 상위 몇 퍼센트에 속하는지 나타냅니다. 예를 들어, 1%는 최고 순위.
      - **`nextLevelExp`**: 다음 레벨로 올라가기 위해 필요한 총 경험치.
      - **`remainingExpForNextLevel`**: 다음 레벨로 올라가기 위해 남은 경험치.
      - **`progressPercentToNextLevel`**: 현재 레벨에서 다음 레벨로 진행된 비율 (백분율).
      - **`Integer rankChange`**: 전날 자정과 비교한 랭크 변화. ( 어제 5등이고 현재 3등이면 -> 2) 로 표시됩니다

      **입력 파라미터 값:**
      
      - **`필요없음`**: 추가 입력 파라미터 없이 JWT 토큰만으로 회원 정보를 조회합니다.

      **반환 파라미터 값:**
      
      - **`Member member`**: 회원의 기본 정보.
      - **`MemberInteraction memberInteraction`**: 회원의 콘텐츠 상호작용 정보.
      - **`Long exp`**: 현재 사용자의 총 경험치.
      - **`Integer expRank`**: 회원의 경험치 순위.
      - **`Double expRankPercentile`**: 상위 몇 퍼센트에 속하는지 백분율로 반환.
      - **`Long nextLevelExp`**: 다음 레벨로 올라가기 위한 총 경험치.
      - **`Long remainingExpForNextLevel`**: 다음 레벨까지 남은 경험치.
      - **`Double progressPercentToNextLevel`**: 다음 레벨로 향한 경험치 진행도(백분율).
      - **`Integer rankChange`**: 전날 자정과 비교한 랭크 변화. ( 어제 5등이고 현재 3등이면 -> 2) 로 표시됩니다
      """
  )
  ResponseEntity<MemberDto> getProfile(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "좋아요한 글",
      description = """
        **좋아요한 글**

        회원이 좋아요한 글을 제공합니다. 이 API는 사용자가 좋아요한 모든 게시글을 조회합니다.

        **JWT 토큰 필요:**
        
        이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

        **입력 파라미터 값:**

        - **`필요없음`**

        **반환 파라미터 값:**

        - **`List<BasePost> likedOfficialGames`**: 좋아요한 공식 게임 게시글 리스트
        - **`List<BasePost> likedCreationGames`**: 좋아요한 창작 게임 게시글 리스트
        - **`List<BasePost> likedIntros`**: 좋아요한 소개글 리스트
        """
  )
  ResponseEntity<MemberDto> getLikedPosts(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "내가 작성한 글",
      description = """
    **내가 작성한 글**

    회원(본인)이 작성한 글을 제공합니다. 이 API는 사용자가 작성한 모든 게시글을 조회합니다.

    **JWT 토큰 필요:**

    이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

    **입력 파라미터 값:**

    - **`필요없음`**

    **반환 파라미터 값:**

    - **`List<CreationGame> myCreationGames`**: 회원(본인)이 작성한 창작 게임 게시글 리스트
    - **`List<Intro> myIntros`**: 회원(본인)이 작성한 소개글 리스트
    """
  )
  ResponseEntity<MemberDto> getMyPosts(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "즐겨찾기한 글",
      description = """
        **즐겨찾기한 글**

        회원이 즐겨찾기한 글을 제공합니다. 이 API는 사용자가 즐겨찾기한 모든 게시글을 조회합니다.

        **JWT 토큰 필요:**
        
        이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

        **입력 파라미터 값:**

        - **`필요없음`**

        **반환 파라미터 값:**

        - **`List<BasePost> bookmarkedOfficialGameIds`**: 즐겨찾기한 공식 게임 게시글 리스트
        - **`List<BasePost> bookmarkedCreationGameIds`**: 즐겨찾기한 창작 게임 게시글 리스트
        - **`List<BasePost> bookmarkedIntroIds`**: 즐겨찾기한 소개글 리스트
        """
  )
  ResponseEntity<MemberDto> getBookmarkedPosts(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "회원 프로필 이미지 업데이트",
      description = """
        **회원 프로필 이미지 업데이트**

        회원의 프로필 이미지를 업데이트합니다. 이 API는 사용자가 프로필 이미지를 업로드 및 변경할 수 있게 합니다.

        **JWT 토큰 필요:**
        
        이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

        **입력 파라미터 값:**

        - **`MultipartFile multipartFile`**: 업로드할 프로필 이미지 파일

        **반환 파라미터 값:**

        - **`Member member`**: 업데이트된 프로필 이미지를 포함한 회원 정보
        """
  )
  ResponseEntity<MemberDto> updateMemberProfileImage(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "회원 닉네임 업데이트",
      description = """
        **회원 닉네임 업데이트**

        회원의 프로필 닉네임을 업데이트합니다. 이 API는 사용자가 닉네임을 변경할 수 있게 합니다.

        **JWT 토큰 필요:**
        이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

        **입력 파라미터 값:**

        - **`String nickname`**: 새로 설정할 닉네임

        **반환 파라미터 값:**

        - **`Member member`**: 닉네임이 업데이트된 회원 정보

        **예외 상황:**

        - 닉네임이 이미 존재하는 경우 `CustomException`이 발생합니다.
        """
  )
  ResponseEntity<MemberDto> changeNickname(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "알림 수신 설정 변경",
      description = """
        **알림 수신 설정 변경**

        회원의 알림 수신 설정을 변경합니다. 이 API를 통해 사용자는 알림 수신 여부를 활성화하거나 비활성화할 수 있습니다.

        **JWT 토큰 필요:**
        
        이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

        **입력 파라미터 값:**

        - **`Boolean isNotificationEnabled`**: 알림 수신 여부 (예: "true" or "false")
          `기본값: true`

        **반환 파라미터 값:**

        - **`Member member`**: 알림 수신 설정이 업데이트된 회원 정보
        """
  )
  ResponseEntity<MemberDto> changeNotificationSetting(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "닉네임 중복 확인",
      description = """
        **닉네임 중복 확인**

        회원 가입 또는 닉네임 변경 시, 사용자가 입력한 닉네임이 이미 사용 중인지 확인합니다.

        **JWT 토큰 필요:**
        
        이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

        **입력 파라미터 값:**

        - **`String nickname`**: 중복 여부를 확인할 닉네임

        **반환 파라미터 값:**

        - **`Boolean isExistingNickname`**: 닉네임 중복 여부 (`true`: 중복 있음, `false`: 중복 없음)
        """
  )
  ResponseEntity<MemberDto> checkDuplicateNickname(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "경험치 정보 업데이트",
      description = """
    **경험치 정보 업데이트**

    이 API는 사용자가 경험치를 기반으로 한 순위 정보를 다시 계산하고, 업데이트된 순위를 반환합니다. 
    순위는 경험치에 따라 결정되며, 상위 퍼센트와 다음 레벨로의 진행도를 함께 제공합니다.

    **JWT 토큰 필요:**
    이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

    **경험치 관련 반환 값:**
    - **`exp`**: 현재 사용자의 총 경험치.
    - **`expRank`**: 회원의 경험치 순위 (1등이 가장 높음).
    - **`expRankPercentile`**: 회원이 상위 몇 퍼센트에 속하는지 나타냅니다.
    - **`nextLevelExp`**: 다음 레벨로 올라가기 위해 필요한 총 경험치.
    - **`remainingExpForNextLevel`**: 다음 레벨로 올라가기 위해 남은 경험치.
    - **`progressPercentToNextLevel`**: 현재 레벨에서 다음 레벨로 진행된 비율 (백분율).
    - **`Integer rankChange`**: 전날 자정과 비교한 랭크 변화. ( 어제 5등이고 현재 3등이면 -> 2) 로 표시됩니다

    **입력 파라미터 값:**
    - **`필요없음`**: 추가 입력 파라미터 없이 JWT 토큰만으로 회원의 경험치 순위를 조회합니다.

    **반환 파라미터 값:**
    - **`Long exp`**: 현재 사용자의 총 경험치.
    - **`Integer expRank`**: 회원의 경험치 순위.
    - **`Double expRankPercentile`**: 상위 몇 퍼센트에 속하는지 백분율로 반환.
    - **`Long nextLevelExp`**: 다음 레벨로 올라가기 위한 총 경험치.
    - **`Long remainingExpForNextLevel`**: 다음 레벨까지 남은 경험치.
    - **`Double progressPercentToNextLevel`**: 다음 레벨로 향한 경험치 진행도(백분율).
    - **`Integer rankChange`**: 전날 자정과 비교한 랭크 변화. ( 어제 5등이고 현재 3등이면 -> 2) 로 표시됩니다
    """
  )
  ResponseEntity<MemberDto> reloadRankInfo(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "경험치 변동 내역 조회",
      description = """
        **경험치 변동 내역 조회**

        이 API는 사용자가 경험치 변동 내역을 조회할 수 있습니다. 
        페이징 처리가 되어 있으며, 페이지 번호와 크기를 요청 파라미터로 보낼 수 있습니다.

        **JWT 토큰 필요:**
        이 API는 인증이 필요합니다. 요청 시 `Authorization` 헤더에 `Bearer` 형식으로 JWT 토큰을 포함해야 합니다.

        **입력 파라미터 값:**
        - **`int pageNumber`**: 페이지 번호 (기본값: 0, 첫 페이지)
        - **`int pageSize`**: 페이지 크기 (기본값: 10)

        **반환 파라미터 값:**
        - **`List<ExpLog>`**: 경험치 변동 내역 리스트
        - **`int totalPages`**: 총 페이지 수
        - **`int currentPage`**: 현재 페이지 번호
        - **`long totalElements`**: 총 내역 개수
        """
  )
  ResponseEntity<MemberDto> getExpLogs(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @ModelAttribute MemberCommand command);

  @Operation(
      summary = "회원 경험치 랭킹 목록",
      description = """
        **회원 경험치 랭킹 목록**

        이 API는 사용자가 회원 경험치 랭킹 목록을 조회할 수 있습니다. 
        랭킹정보는 3시간마다 업데이트됩니다
        페이징 처리가 되어 있으며, 페이지 번호와 크기를 요청 파라미터로 보낼 수 있습니다.

        **JWT 토큰 인증 필요없음:**

        **입력 파라미터 값:**
        - **`int pageNumber`**: 페이지 번호 (기본값: 0, 첫 페이지)
        - **`int pageSize`**: 페이지 크기 (기본값: 10)

        **반환 파라미터 값:**
        - **`Page<DailyMemberExpRank>`**: 회원 경험치 랭킹 목록록
        """
  )
  ResponseEntity<MemberDto> getDailyMemberExpRankings(
      @ModelAttribute MemberCommand command);
}