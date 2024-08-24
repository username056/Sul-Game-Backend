package org.sejong.sulgamewiki.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.web.client.RestTemplate;

public class MockUtil {

  /**
   * 가짜 회원에 대한 정보를 생성
   *
   * @return String nickname String email LocalDate birthDate
   */
  public static MemberDto getMockMemberInfo() {
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.heropy.dev/v0/users";
    String response = restTemplate.getForObject(url, String.class);
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode root = null;

    try {
      root = objectMapper.readTree(response);
    } catch (JsonProcessingException e) {
      throw new CustomException(ErrorCode.MOCK_MEMBER_MAPPING_ERROR);
    }

    JsonNode user = root.get("users").get(0);
    int age = user.get("age").asInt();

    // 나이 14세 이상
    if (age < 14) {
      age = 14;
    }

    String email = user.get("emails").get(0).asText();
    String nickname = user.get("name").asText();
    LocalDate birthDate = LocalDate.now().minusYears(age);

    // 결과 출력
    System.out.println("fullName: " + nickname);
    System.out.println("Email: " + email);
    System.out.println("birthDate: " + birthDate);

    return MemberDto.builder()
        .name(nickname)
        .email(email)
        .birthDate(birthDate)
        .build();

  }
}

