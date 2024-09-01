package org.sejong.sulgamewiki.service;

import java.util.List;
import java.util.StringJoiner;
import org.sejong.sulgamewiki.object.AuthDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AuthService {

  @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
  private String googleRedirectUri;

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String googleClientId;

  @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
  private String googleAuthorizationUri;

  @Value("${spring.security.oauth2.client.registration.google.scope}")
  private List<String> googleScopes;

  public AuthDto getGoogleRedirectUrl() {
    String responseType = "code";
    String scope = String.join(" ", googleScopes);

    String redirectUrl = UriComponentsBuilder.fromHttpUrl(googleAuthorizationUri)
        .queryParam("client_id", googleClientId)
        .queryParam("redirect_uri", googleRedirectUri)
        .queryParam("response_type", responseType)
        .queryParam("scope", scope)
        .build()
        .toUriString();

    return AuthDto.builder()
        .googleRedirectUrl(redirectUrl)
        .build();
  }
}
