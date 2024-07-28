package org.sejong.sulgamewiki.common.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

@Configuration
public class GoogleOAuth2Config {
  @Bean
  public ClientRegistrationRepository clientRegistrationRepository(
      @Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId,
      @Value("${spring.security.oauth2.client.registration.google.client-secret}") String clientSecret,
      @Value("${spring.security.oauth2.client.registration.google.redirect-uri}") String redirectUri) {

    ClientRegistration registration = ClientRegistration
        .withRegistrationId("google")
        .clientId(clientId)
        .clientSecret(clientSecret)
        .redirectUri(redirectUri)
        .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
        .tokenUri("https://www.googleapis.com/oauth2/v4/token")
        .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
        .userNameAttributeName(IdTokenClaimNames.SUB)
        .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
        .clientName("Google")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope("openid", "profile", "email")
        .build();

    return new InMemoryClientRegistrationRepository(registration);
  }
}