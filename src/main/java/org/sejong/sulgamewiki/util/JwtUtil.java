package org.sejong.sulgamewiki.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.util.auth.CustomOAuth2UserService;
import org.sejong.sulgamewiki.util.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

  private final CustomOAuth2UserService customUserDetailsService;

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.access-exp-time}")
  private Long accessTokenExpTime;

  @Value("${jwt.refresh-exp-time}")
  private Long refreshTokenExpTime;

  private static final String ROLE = "role";

  public String createAccessToken(CustomUserDetails customUserDetails) {
    return createToken(customUserDetails, accessTokenExpTime);
  }

  public String createRefreshToken(CustomUserDetails customUserDetails) {
    return createToken(customUserDetails, refreshTokenExpTime);
  }

  private String createToken(CustomUserDetails customUserDetails, Long expiredAt) {
    Date now = new Date();
    Map<String, Object> headers = new HashMap<>();
    headers.put("typ", "JWT");

    return Jwts.builder()
        .setHeader(headers)
        .setIssuer("SULGAMEWIKI")
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + expiredAt))
        .setSubject(customUserDetails.getUsername())
        .claim(ROLE, customUserDetails.getMember().getRole())
        .signWith(getSigningKey())
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(getSigningKey())
          .parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT claims string is empty.", e);
    }
    return false;
  }

  public Authentication getAuthentication(String token) {
    Claims claims = getClaims(token);

    Set<SimpleGrantedAuthority> authorities =
        Collections.singleton(new SimpleGrantedAuthority(claims.get(ROLE, String.class)));

    CustomUserDetails customUserDetails =
        customUserDetailsService.loadUserByUsername(claims.getSubject());

    return new UsernamePasswordAuthenticationToken(customUserDetails, token, authorities);
  }

  public Claims getClaims(String token) {
    return Jwts.parser()
        .setSigningKey(getSigningKey())
        .parseClaimsJws(token)
        .getBody();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
