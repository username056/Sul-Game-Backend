package org.sejong.sulgamewiki.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.object.Report;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.repository.ReportRepository;
import org.sejong.sulgamewiki.object.CustomUserDetails;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

  private final BasePostRepository basePostRepository;
  private final ReportRepository reportRepository;
  private final MemberRepository memberRepository;
  private final MemberInteractionRepository memberInteractionRepository;
  private final BaseMediaService baseMediaService;

  @Override
  public CustomUserDetails loadUserByUsername(String stringMemberId)
      throws UsernameNotFoundException {
    Member member = memberRepository.findById(Long.parseLong(stringMemberId))
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    return new CustomUserDetails(member);
  }

  @Transactional
  public MemberDto completeRegistration(MemberCommand command) {
    // Member 검증
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (member.getAccountStatus() != AccountStatus.PENDING) {
      log.error("회원 AccountStatus 비정상 : {}", member.getAccountStatus());
      throw new CustomException(ErrorCode.INVALID_ACCOUNT_STATUS);
    }

    // Member 업데이트 및 저장
    member.setBirthDate(command.getBirthDate());
    member.setUniversity(command.getUniversity());
    member.setNickname(command.getNickname());
    member.setAccountStatus(AccountStatus.ACTIVE);

    Member updatedMember = memberRepository.save(member);

    return MemberDto.builder()
        .member(updatedMember)
        .build();
  }


  @Transactional(readOnly = true)
  @LogMonitoringInvocation
  public MemberDto getProfile(MemberCommand command) {
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    MemberInteraction memberContent
        = memberInteractionRepository.findByMember(member)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    return MemberDto.builder()
        .member(member)
        .memberInteraction(memberContent)
        .build();
  }

  // 관리자 기능
  @Transactional(readOnly = true)
  public MemberDto getReport(MemberCommand command) {
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    List<Report> reports = reportRepository.findByReporter(member);
    return MemberDto.builder()
        .reports(reports)
        .build();
  }


  @Transactional(readOnly = true)
  public MemberDto getLikedPosts(MemberCommand command) {
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    MemberInteraction memberContent
        = memberInteractionRepository.findByMember(member)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    List<BasePost> likedIntros =
        basePostRepository.findByBasePostIdIn(memberContent.getLikedIntroIds());

    List<BasePost> likedCreationGame =
        basePostRepository.findByBasePostIdIn(memberContent.getLikedCreationGameIds());

    List<BasePost> likedOfficialGame =
        basePostRepository.findByBasePostIdIn(memberContent.getLikedOfficialGameIds());

    return MemberDto.builder()
        .likedOfficialGames(likedOfficialGame)
        .likedCreationGames(likedCreationGame)
        .likedIntros(likedIntros)
        .build();
  }

  @Transactional(readOnly = true)
  public MemberDto getBookmarkedPosts(MemberCommand command) {
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    MemberInteraction memberContent
        = memberInteractionRepository.findByMember(member)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

    List<BasePost> bookmarkedIntroIds =
        basePostRepository.findByBasePostIdIn(memberContent.getBookmarkedIntroIds());

    List<BasePost> bookmarkedCreationGameIds =
        basePostRepository.findByBasePostIdIn(memberContent.getBookmarkedCreationGameIds());

    List<BasePost> bookmarkedOfficialGameIds =
        basePostRepository.findByBasePostIdIn(memberContent.getBookmarkedOfficialGameIds());

    return MemberDto.builder()
        .likedOfficialGames(bookmarkedOfficialGameIds)
        .likedCreationGames(bookmarkedCreationGameIds)
        .likedIntros(bookmarkedIntroIds)
        .build();
  }

  @Transactional
  public void deleteMember(MemberCommand command) {
    memberRepository.deleteById(command.getMemberId());
  }

  @Transactional
  public MemberDto updateMemberProfileImage(MemberCommand command) {
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    member.setProfileUrl(
        baseMediaService.uploadMemberProfileImage(
            command.getMultipartFile(), member.getMemberId()));
    return MemberDto.builder()
        .member(member)
        .build();
  }

  /**
   * 기존 회원의 닉네임을 업데이트합니다.
   * <p>
   * 이 메서드는 제공된 닉네임이 이미 저장소에 존재하는지 확인합니다. 만약 닉네임이 이미 사용 중인 경우, `CustomException`이 발생하며 오류 코드 `NICKNAME_ALREADY_EXISTS`가
   * 반환됩니다. 닉네임이 사용 가능할 경우, 회원의 닉네임을 업데이트하고 변경 사항을 저장소에 저장합니다.
   *
   * @param command 회원의 ID와 새로 설정할 닉네임이 포함된 객체입니다.
   * @return 새로운 닉네임으로 업데이트된 회원 정보를 포함한 MemberDto 객체를 반환합니다.
   * @throws CustomException 회원을 찾을 수 없거나 닉네임이 이미 존재하는 경우 예외가 발생합니다.
   */
  @Transactional
  public MemberDto changeNickname(MemberCommand command) {
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (memberRepository.existsByNickname(command.getNickname())) {
      throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
    }

    member.setNickname(command.getNickname());
    Member savedMember = memberRepository.save(member);

    return MemberDto.builder()
        .member(savedMember)
        .build();
  }

  @Transactional
  public MemberDto changeNotificationSetting(MemberCommand command) {
    Member member = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    member.setIsNotificationEnabled(command.getIsNotificationEnabled());
    Member savedMember = memberRepository.save(member);

    return MemberDto.builder()
        .member(savedMember)
        .build();
  }

  @Transactional(readOnly = true)
  public MemberDto isDuplicationNickname(MemberCommand command) {
    Member loginedMember = memberRepository.findById(command.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Optional<Member> checkingMember = memberRepository.findByNickname(command.getNickname());
    return MemberDto.builder()
        .isExistingNickname(checkingMember.isPresent())
        .build();
  }
}
