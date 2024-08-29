package org.sejong.sulgamewiki.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.CreationGame;
import org.sejong.sulgamewiki.object.Intro;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberContentInteraction;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.TestCommand;
import org.sejong.sulgamewiki.object.TestDto;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.object.constants.ExpLevel;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberContentInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.MockUtil;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
  private final MemberRepository memberRepository;
  private final MemberContentInteractionRepository memberContentInteractionRepository;
  private final BasePostRepository basePostRepository;

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
  public TestDto createMockMember() {
    // API를 통해서 Mock Member 정보 가져옴
    TestDto dto = MockUtil.getMockMemberInfo();

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

    return TestDto
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
  @Transactional
  public TestDto deleteAllMockMember() {
    memberContentInteractionRepository.deleteAll();
    memberRepository.deleteAll();

    return TestDto.builder()
        .members(memberRepository.findAll())
        .memberContentInteractions(memberContentInteractionRepository.findAll())
        .build();
  }

  @Transactional(readOnly = true)
  public TestDto getAllMember() {
    return TestDto.builder()
        .members(memberRepository.findAll())
        .build();
  }

  @Transactional
  public TestDto createMockPosts(TestCommand command) {
    List<Long> allMemberIds = memberRepository.findAllMemberIds();
    Random random = new Random();

    List<BasePost> createdPosts = new ArrayList<>();

    // Intro 포스트 생성
    for (int i = 0; i < command.getEachPostCreateCount(); i++) {
      Long randomMemberId = allMemberIds.get(random.nextInt(allMemberIds.size()));
      Intro intro = Intro.builder()
          .title("Intro Title " + UUID.randomUUID().toString().substring(0, 8))
          .introduction("Intro Introduction " + UUID.randomUUID().toString().substring(0, 8))
          .description("Intro Description " + UUID.randomUUID().toString().substring(0, 8))
          .lyrics("Intro Lyrics " + random.nextInt(100))
          .likes(random.nextInt(100))  // 좋아요 수를 0부터 100 사이에서 랜덤하게 설정
          .views(random.nextInt(1000)) // 조회수를 0부터 1000 사이에서 랜덤하게 설정
          .reportedCount(random.nextInt(10)) // 신고 횟수를 0부터 10 사이에서 랜덤하게 설정
          .dailyScore(random.nextInt(50)) // 일일 점수를 0부터 50 사이에서 랜덤하게 설정
          .weeklyScore(random.nextInt(100)) // 주간 점수를 0부터 100 사이에서 랜덤하게 설정
          .member(memberRepository.findById(randomMemberId).orElseThrow())
          .build();

      createdPosts.add(basePostRepository.save(intro));
    }

    // CreationGame 포스트 생성
    for (int i = 0; i < command.getEachPostCreateCount(); i++) {
      Long randomMemberId = allMemberIds.get(random.nextInt(allMemberIds.size()));
      CreationGame creationGame = CreationGame.builder()
          .title("CreationGame Title " + UUID.randomUUID().toString().substring(0, 8))
          .introduction("CreationGame Introduction " + UUID.randomUUID().toString().substring(0, 8))
          .description("CreationGame Description " + UUID.randomUUID().toString().substring(0, 8))
          .likes(random.nextInt(100))  // 좋아요 수를 0부터 100 사이에서 랜덤하게 설정
          .views(random.nextInt(1000)) // 조회수를 0부터 1000 사이에서 랜덤하게 설정
          .reportedCount(random.nextInt(10)) // 신고 횟수를 0부터 10 사이에서 랜덤하게 설정
          .dailyScore(random.nextInt(50)) // 일일 점수를 0부터 50 사이에서 랜덤하게 설정
          .weeklyScore(random.nextInt(100)) // 주간 점수를 0부터 100 사이에서 랜덤하게 설정
          .member(memberRepository.findById(randomMemberId).orElseThrow())
          .build();

      createdPosts.add(basePostRepository.save(creationGame));
    }

    // OfficialGame 포스트 생성
    for (int i = 0; i < command.getEachPostCreateCount(); i++) {
      Long randomMemberId = allMemberIds.get(random.nextInt(allMemberIds.size()));
      OfficialGame officialGame = OfficialGame.builder()
          .title("OfficialGame Title " + UUID.randomUUID().toString().substring(0, 8))
          .introduction("OfficialGame Introduction " + UUID.randomUUID().toString().substring(0, 8))
          .description("OfficialGame Description " + UUID.randomUUID().toString().substring(0, 8))
          .likes(random.nextInt(100))  // 좋아요 수를 0부터 100 사이에서 랜덤하게 설정
          .views(random.nextInt(1000)) // 조회수를 0부터 1000 사이에서 랜덤하게 설정
          .reportedCount(random.nextInt(10)) // 신고 횟수를 0부터 10 사이에서 랜덤하게 설정
          .dailyScore(random.nextInt(50)) // 일일 점수를 0부터 50 사이에서 랜덤하게 설정
          .weeklyScore(random.nextInt(100)) // 주간 점수를 0부터 100 사이에서 랜덤하게 설정
          .member(memberRepository.findById(randomMemberId).orElseThrow())
          .build();

      createdPosts.add(basePostRepository.save(officialGame));
    }

    log.info("총 {}개의 포스트가 생성되었습니다.", createdPosts.size());

    return TestDto.builder()
        .basePosts(createdPosts)
        .build();
  }
}
