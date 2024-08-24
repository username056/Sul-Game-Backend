package org.sejong.sulgamewiki.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberContentInteraction;
import org.sejong.sulgamewiki.object.MockDto;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.object.constants.ExpLevel;
import org.sejong.sulgamewiki.repository.MemberContentInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.MockUtil;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockService {
  private final MemberRepository memberRepository;
  private final MemberContentInteractionRepository memberContentInteractionRepository;

  /**
   * 설명: 새로운 Mock Member를 생성 DB저장
   *
   * 1. MockUtil을 사용하여 가짜 회원 정보를 가져옴
   * 2. 해당 정보를 바탕으로 Member 엔터티를 생성 및 DB 저장
   * 3. 새로운 MemberContentInteraction 엔터티를 생성 및 DB 저장
   *
   * @return MockDto
   * Member member;
   * MemberContentInteraction memberContentInteraction;
   */
  @LogMonitoringInvocation
  @Transactional
  public MockDto createMockMember() {
    // API를 통해서 Mock Member 정보 가져옴
    MockDto dto = MockUtil.getMockMemberInfo();

    // Mock Member 생성
    Member member = Member.builder()
        .email(dto.getEmail()) // 임의의 이메일
        .nickname(dto.getNickname())
        .birthDate(dto.getBirthDate())
        .college("세종대학교")
        .isUniversityPublic(true)
        .isNotificationEnabled(true)
        .accountStatus(AccountStatus.PENDING)
        .lastLoginTime(LocalDateTime.now())
        .exp(0L)
        .expLevel(ExpLevel.D)
        .build();

    MemberContentInteraction memberContent = MemberContentInteraction.builder()
        .member(member)
        .totalLikeCount(0)
        .totalCommentCount(0)
        .totalPostCount(0)
        .totalCommentLikeCount(0)
        .totalPostLikeCount(0)
        .totalMediaCount(0)
        .likedOfficialGameIds(new ArrayList<>())
        .likedCreationGameIds(new ArrayList<>())
        .likedIntroIds(new ArrayList<>())
        .bookmarkedOfficialGameIds(new ArrayList<>())
        .bookmarkedCreationGameIds(new ArrayList<>())
        .bookmarkedIntroIds(new ArrayList<>())
        .build();

    // DB 저장
    Member savedMember = memberRepository.save(member);
    MemberContentInteraction savedMemberContent
        = memberContentInteractionRepository.save(memberContent);

    log.info("가짜 회원 생성 : id : {} , email : {}", member.getMemberId(), member.getEmail());

    return MockDto
        .builder()
        .member(savedMember)
        .memberContentInteraction(savedMemberContent)
        .build();
  }

  /**
   * 회원과 관련된 전체 정보를 삭제 합니다
   * 전체 MemberRepository에 대한 정보를 삭제합니다
   * 전체 memberContentInteractionRepository도 삭제합니다.
   *
   * @return MockDto
   * List<Member> members;
   * List<MemberContentInteraction> memberContentInteractions;
   * 전부 비어있어야 정상입니다!!
   *
   */
  public MockDto deleteAllMockMember() {
    memberContentInteractionRepository.deleteAll();
    memberRepository.deleteAll();

    return MockDto.builder()
        .members(memberRepository.findAll())
        .memberContentInteractions(memberContentInteractionRepository.findAll())
        .build();
  }
}
