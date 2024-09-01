package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.AuthDto;
import org.sejong.sulgamewiki.service.AuthService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(
    name = "인증 관리 API",
    description = "인증 관리 API 제공"
)
public class AuthController {
  private final AuthService authService;

  @GetMapping("/google")
  @LogMonitoringInvocation
  public ResponseEntity<AuthDto> getProfile(){
    return ResponseEntity.ok(authService.getGoogleRedirectUrl());
  }
}
