package org.sejong.sulgamewiki.util.config;

import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.service.CustomOAuth2UserService;
import org.sejong.sulgamewiki.service.ExpManagerService;
import org.sejong.sulgamewiki.util.JwtUtil;
import org.sejong.sulgamewiki.util.filter.VisitCountFilter;
import org.sejong.sulgamewiki.util.auth.OAuth2MemberSuccessHandler;
import org.sejong.sulgamewiki.util.filter.TokenAuthenticationFilter;
import org.sejong.sulgamewiki.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final MemberRepository memberRepository;
  private final MemberInteractionRepository memberInteractionRepository;
  private final JwtUtil jwtUtil;
  private final OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final ExpManagerService expManagerService;

  private static final String[] AUTH_WHITELIST = {
      "/", // 기본화면
      "/docs/**", // Swagger
      "/favicon.ico", // 앱아이콘
      "/api/test/**", // 테스트 API
      "/v3/api-docs/**", // Swagger
      "/login", // OAuth 관리페이지
      "/api/auth/refresh-token", // AccessToken 재발행
      "/login/oauth2/code/**", // OAuth 리다이렉션
      "/oauth2/authorization/**", // OAuth 로그인 페이지
      "/api/intro/get",
      "/api/comment/get",
      "/api/official/details",
      "/api/creation/details",
      "/api/official/get-all",
      "/api/home/**"
  };

  private static final String[] ALLOWED_ORIGINS = {
      "https://api.sul-game.info",
      "https://test.sul-game.info",
      "https://www.sul-game.info",
      "http://220.85.169.165:8085",
      "http://220.85.169.165:8086",
      "http://localhost:8080",
      "http://localhost:3000",
      "http://10.0.2.2:8080"
  };

  @Bean
  public WebSecurityCustomizer configure() {
    return (web) -> web.ignoring()
        .requestMatchers(new AntPathRequestMatcher("/static/**"));
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return
        http.cors(cors -> cors
                .configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/members/complete-registration").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/members/profile").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/members/liked-posts").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/members/bookmarked-posts").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/members/profile-image").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/members/nickname").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/members/notification").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/members/check-nickname").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/intro/create").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/intro/delete").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/intro/update").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/comment/create").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/comment/delete").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/comment/update").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/official/create").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/official/delete").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/official/update").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/creation/create").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/creation/delete").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/creation/update").hasRole("USER")
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                .successHandler(oAuth2MemberSuccessHandler)
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new TokenAuthenticationFilter(jwtUtil, Arrays.asList(AUTH_WHITELIST)),
                UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(new VisitCountFilter(memberRepository, memberInteractionRepository, expManagerService),
                TokenAuthenticationFilter.class)
            .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      MemberService memberService) throws Exception {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(memberService);
    authProvider.setPasswordEncoder(bCryptPasswordEncoder);
    return new ProviderManager(authProvider);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList(ALLOWED_ORIGINS));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(Collections.singletonList("*"));
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}