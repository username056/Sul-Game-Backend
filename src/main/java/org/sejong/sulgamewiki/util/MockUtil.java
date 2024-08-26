package org.sejong.sulgamewiki.util;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import org.sejong.sulgamewiki.object.TestDto;

public class MockUtil {

  /**
   * 가짜 회원에 대한 정보를 생성
   *
   * @return MockDto 객체
   * String nickname;
   * String email;
   * LocalDate birthDate
   */
  public static TestDto getMockMemberInfo() {
    Faker faker = new Faker();

    // 무작위 이름, 이메일, 나이 생성
    String nickname = faker.name().fullName();
    String email = faker.internet().emailAddress();
    int age = faker.number().numberBetween(14, 100); // 나이 14세 이상
    LocalDate birthDate = LocalDate.now().minusYears(age);

    return TestDto.builder()
        .nickname(nickname)
        .email(email)
        .birthDate(birthDate)
        .build();
  }
}
