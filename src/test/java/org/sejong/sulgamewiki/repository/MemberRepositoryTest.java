package org.sejong.sulgamewiki.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberContentInteraction;
import org.sejong.sulgamewiki.object.MockDto;
import org.sejong.sulgamewiki.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberContentInteractionRepository memberContentInteractionRepository;

  @Autowired
  private MockService mockService;

  @Test
  @Transactional
  void testCreateAndFindMockMember() {
    // Mock 데이터 생성
    MockDto mockDto = mockService.createMockMember();
    System.out.println("======");
    System.out.println(mockDto.toString());
    System.out.println("======");

    // 저장된 Member 엔터티 검증
    Member savedMember = memberRepository.findById(mockDto.getMember().getMemberId()).orElse(null);

    System.out.println("======");
    System.out.println(savedMember.toString());
    System.out.println("======");

    // 저장된 MemberContentInteraction 엔터티 검증
    MemberContentInteraction savedInteraction = memberContentInteractionRepository.findByMember(savedMember);
    System.out.println("======");
    System.out.println(savedInteraction.toString());
    System.out.println("======");
  }
}
