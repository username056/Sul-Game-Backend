package org.sejong.sulgamewiki.common.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Configuration
public class GoogleOAuth2Config {
  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  String clientId;

  @Value("${spring.security.oauth2.client.registration.google.client-secret}")
  String clientSecret;

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
  }

  private ClientRegistration googleClientRegistration() {
    return ClientRegistrations.fromIssuerLocation("https://accounts.google.com")
        .clientId(clientId)
        .clientSecret(clientSecret)
        .build();
  }

  @Bean
  public OidcUserService oidcUserService() {
    return new OidcUserService() {
      @Override
      public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);
        // 사용자 정보를 추가로 처리하고 저장하는 로직을 추가할 수 있습니다.
        return oidcUser;
      }
    };
  }

}
