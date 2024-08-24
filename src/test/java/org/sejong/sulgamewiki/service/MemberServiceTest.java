package org.sejong.sulgamewiki.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.sejong.sulgamewiki.object.Member;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

public class MemberServiceTest {

  @Test
  public void testCreateMemberFromApi() {
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.heropy.dev/v0/users";
    String response = restTemplate.getForObject(url, String.class);

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode root = objectMapper.readTree(response);

      JsonNode user = root.get("users").get(0); // Get the first user

      String email = user.get("emails").get(0).asText();
      String nickname = user.get("name").asText();
      LocalDate birthDate = LocalDate.now().minusYears(user.get("age").asInt());
      System.out.println(email);
      System.out.println(nickname);
      System.out.println(birthDate);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
