package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.sejong.sulgamewiki.object.AuthCommand;
import org.sejong.sulgamewiki.object.AuthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface AuthControllerDocs {

  @Operation(
      summary = "리프레시 토큰을 통한 액세스 토큰 재발급",
      description = """
          **리프레시 토큰을 통한 액세스 토큰 재발급**

          사용자는 기존에 발급받은 리프레시 토큰을 통해 새로운 액세스 토큰을 요청할 수 있습니다. 이 API는 클라이언트가 리프레시 토큰을 이용해 새로운 액세스 토큰을 발급받는 데 사용됩니다.

          **요청 방법:**
          
          요청 시 리프레시 토큰을 `ModelAttribute`로 포함하여 요청해야 합니다. 서버는 이 토큰의 유효성을 검사하고, 새로운 액세스 토큰을 발급합니다.

          **입력 파라미터:**
          - `refreshToken`: 리프레시 토큰이 `ModelAttribute`로 전송됩니다.

          **반환 값:**
          - `accessToken`: 새로 발급된 액세스 토큰이 반환됩니다.
        """
  )
  ResponseEntity<AuthDto> refreshToken(
      @ModelAttribute AuthCommand command
  );
}
