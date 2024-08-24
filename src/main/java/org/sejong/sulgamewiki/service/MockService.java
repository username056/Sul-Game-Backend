package org.sejong.sulgamewiki.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberContentInteraction;
import org.sejong.sulgamewiki.object.MemberDto;
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

  @LogMonitoringInvocation
  @Transactional
  public MemberDto createMockMember() {
    // API를 통해서 Mock Member 정보 가져옴
    MemberDto dto = MockUtil.getMockMemberInfo();

    // Mock Member 생성
    Member member = Member.builder()
        .email(dto.getEmail()) // 임의의 이메일
        .nickname(dto.getName())
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

    return MemberDto
        .builder()
        .member(savedMember)
        .memberContentInteraction(savedMemberContent)
        .build();
  }
}
