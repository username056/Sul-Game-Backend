package org.sejong.sulgamewiki.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.util.auth.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  private static final String[] WHITELIST = {
      "/",
      "/api/signup",
      "/api/login",
      "/login/**",
      "/signup",
      "/docs/**",
      "/v3/api-docs/**",
      "/login/oauth2/code/google",
      "/api/members/complete-registration"
  };

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String URI = request.getRequestURI();

    // WHITELIST URL인 경우 JWT 토큰 검증을 생략
    if (isWhitelistedPath(URI)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = getAccessToken(request);

    if (token != null && jwtUtil.validateToken(token)) {
      Authentication authentication = jwtUtil.getAuthentication(token);
      if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Set Authentication to security context for '{}', uri: {}", ((CustomUserDetails) authentication.getPrincipal()).getUsername(), URI);
      } else {
        log.warn("Authentication object is null or not instance of CustomUserDetails");
      }
    } else {
      log.info("No valid JWT token found, uri: {}", URI);
    }

    filterChain.doFilter(request, response);
  }

  private boolean isWhitelistedPath(String uri) {
    return Arrays.stream(WHITELIST).anyMatch(path -> antPathMatcher.match(path, uri));
  }

  private String getAccessToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}