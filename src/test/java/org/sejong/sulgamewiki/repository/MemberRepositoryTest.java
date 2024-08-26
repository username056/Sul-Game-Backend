package org.sejong.sulgamewiki.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberContentInteraction;
import org.sejong.sulgamewiki.object.TestDto;
import org.sejong.sulgamewiki.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberContentInteractionRepository memberContentInteractionRepository;

  @Autowired
  private TestService testService;

  @Test
  @Transactional
  public void mainTest() {
    testCreateAndFindMockMember();
  }

  void testCreateAndFindMockMember() {
    log.info("회원 정상 생성 확인");
    // Mock 데이터 생성
    TestDto testDto = testService.createMockMember();

    log.info("생성된 가짜회원 ID: {}", testDto.getMember().getMemberId());
    log.info("생성된 가짜회원 Email: {}", testDto.getMember().getEmail());

    log.info("생성된 회원 활동 정보 ID: {}", testDto.getMemberContentInteraction().getId());
    log.info("생성된 회원 활동 정보의 회원 ID: {}", testDto.getMemberContentInteraction().getMember().getMemberId());

    // 저장된 Member 엔터티 검증
    Member savedMember = memberRepository.findById(testDto.getMember().getMemberId()).orElse(null);

    // 저장된 MemberContentInteraction 엔터티 검증
    MemberContentInteraction savedInteraction = memberContentInteractionRepository.findByMember(savedMember);

    log.info("조회한 가짜회원 ID: {}", testDto.getMember().getMemberId());
    log.info("조회한 가짜회원 Email: {}", testDto.getMember().getEmail());

    log.info("조회한 회원 활동 정보 ID: {}", testDto.getMemberContentInteraction().getId());
    log.info("조회한 회원 활동 정보의 회원 ID: {}", testDto.getMemberContentInteraction().getMember().getMemberId());

    assertEquals(testDto.getMember(), savedMember, "회원정보 일치 확인");
    assertEquals(testDto.getMemberContentInteraction(), savedInteraction, "Created and Saved MemberContentInteractions should be equal");
  }
}
