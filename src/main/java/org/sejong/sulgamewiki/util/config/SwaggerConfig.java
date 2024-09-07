package org.sejong.sulgamewiki.util.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "🍻 술겜위키 : SULGAME WIKI 🍻",
        description = """
            **술게임의 모든 것을 여기서 만나보세요!**  
            다양한 술게임을 확인하고, 직접 만든 술게임과 인트로를 공유할 수 있는 플랫폼
            
            ### 🔗 **빠르게 로그인하기:**
            - 🔐 **[메인 서버 로그인](https://api.sul-game.info/login)**  
            
            - 🔐 **[로컬 서버 로그인](http://localhost:8080/login)**  
              
            - 🔐 **[테스트 서버 로그인](https://test.sul-game.info/login)**  

            ### 📲 **앱 다운로드 및 정보:**
            - 📥 **[술겜위키 APK 다운로드](http://220.85.169.165/sul-game/)**  
              > 최신 버전의 APK 파일을 다운로드하고 술게임 정보를 쉽게 접하세요.
            
            ### 💻 **GitHub 저장소:**
            - 🛠️ **[백엔드 소스코드](https://github.com/Sul-Game-Wiki/Sul-Game-Backend)**  
              > 백엔드 관련 소스코드를 확인하고 기여해보세요
              
            - 🎨 **[프론트엔드 소스코드](https://github.com/Sul-Game-Wiki/Sul-Game-Frontend)**  
              > 프론트엔드 디자인 및 로직을 확인해보세요
              
            ### ⚠️ **API 테스트 공지사항:**
            - **참고:** 만약 응답에 문제가 있다면,
              Swagger UI에서 `Send empty value` 체크박스가 선택되어 있지 않은지 확인해 주세요.
              `체크박스 해제`한 후 다시 시도해 주세요.
              
            - **MultipartFile, Object : 에서 잘 발생합니다. Can't Parse Json 오류가 생기면 이부분들을 체크해제해주세요**
              
            <img src="http://220.85.169.165/sul-game/images/swagger_error_message.png" alt="에러메시지 이미지" width="300"/>
            <img src="http://220.85.169.165/sul-game/images/swagger_check_box.png" alt="체크박스 이미지" width="200"/>
              
            """,
        version = "0.1v"),
    servers = {
        @Server(url = "https://api.sul-game.info", description = "메인 서버"),
        @Server(url = "http://localhost:8080", description = "로컬 서버"),
        @Server(url = "https://test.sul-game.info", description = "테스트 서버")
    }
)
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    SecurityScheme apiKey = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .in(SecurityScheme.In.HEADER)
        .name("Authorization")
        .scheme("bearer")
        .bearerFormat("JWT");

    SecurityRequirement securityRequirement = new SecurityRequirement()
        .addList("Bearer Token");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
        .addSecurityItem(securityRequirement)
        .servers(List.of(
                new io.swagger.v3.oas.models.servers.Server()
                    .url("http://localhost:8080")
                    .description("Local Server"),
                new io.swagger.v3.oas.models.servers.Server()
                    .url("https://api.sul-game.info")
                    .description("Main Server")
            )
        );
  }
}
