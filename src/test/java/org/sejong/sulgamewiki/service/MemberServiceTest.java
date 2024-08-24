package org.sejong.sulgamewiki.service;

import java.time.LocalDate;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;  // 이 부분 추가
import java.time.LocalDate;

public class MemberServiceTest {

  @Test
  public void testCreateMemberFromApi() {
    Faker faker = new Faker();

    // 무작위 이름, 이메일, 나이 생성
    String nickname = faker.name().fullName();
    String email = faker.internet().emailAddress();
    int age = faker.number().numberBetween(14, 100); // 나이 14세 이상
    LocalDate birthDate = LocalDate.now().minusYears(age);

    // 결과 출력
    System.out.println("Nickname: " + nickname);
    System.out.println("Email: " + email);
    System.out.println("BirthDate: " + birthDate);
  }
}

