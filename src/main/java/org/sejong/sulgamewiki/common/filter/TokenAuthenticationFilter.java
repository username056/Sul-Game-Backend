package org.sejong.sulgamewiki.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.common.auth.application.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  // Security와 JWT 인증 생략하는 URI
  private static final String[] WHITELIST = {
      "/", // 기본화면
      "/api/**", //FIXME: 일시적으로 전체 API 주소 허용 (삭제해야함)
      "/api/signup", // 회원가입
      "/api/login", // 로그인
      "/login/**",
      "/signup",
      "/docs/**", // Swagger
      "/v3/api-docs/**", // Swagger
      "/login/oauth2/code/google", // OAuth 리디렉션 URI
      "/api/members/complete-registration"
  };


  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String URI = request.getRequestURI();
    String authorizationHeader = request.getHeader("Authorization");

    // WHITELIST URL 인 경우 -> JWT Token Validation 하지않는다.
    if (Arrays.stream(WHITELIST)
        .anyMatch(whiteListUri -> antPathMatcher.match(whiteListUri, URI))) {
      log.info("WHILELIST 주소로 토큰검사 안함");
      // Token 검사 생략
      filterChain.doFilter(request, response);
      return;
    }

    // 이외 주소 Token 검사
    String token = getAccessToken(authorizationHeader);

    if (token != null && !token.isEmpty() && jwtUtil.validateToken(token)) { //토큰이 없거나 비어있는 경우
      log.info("Token 검사로직 실행");
      Authentication authentication = jwtUtil.getAuthentication(token);
      log.info("doFilterInternal() token : "+ token);
      log.info("doFilterInternal() authentication : "+ authentication.toString());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      log.info("유효한 JWT 토큰이 없습니다.");
    }

    filterChain.doFilter(request, response);
  }

  private String getAccessToken(String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith(
        "Bearer ")) {
      return authorizationHeader.substring("Bearer ".length());
    }
    return null;
  }
}

