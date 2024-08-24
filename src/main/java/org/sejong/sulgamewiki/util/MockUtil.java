package org.sejong.sulgamewiki.util;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import org.sejong.sulgamewiki.object.MemberDto;

public class MockUtil {

  /**
   * 가짜 회원에 대한 정보를 생성
   *
   * @return MemberDto 객체 (nickname, email, birthDate 포함)
   */
  public static MemberDto getMockMemberInfo() {
    Faker faker = new Faker();

    // 무작위 이름, 이메일, 나이 생성
    String nickname = faker.name().fullName();
    String email = faker.internet().emailAddress();
    int age = faker.number().numberBetween(14, 100); // 나이 14세 이상
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
