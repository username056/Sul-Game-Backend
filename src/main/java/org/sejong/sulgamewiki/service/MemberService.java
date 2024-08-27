package org.sejong.sulgamewiki.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberCommand;
import org.sejong.sulgamewiki.object.MemberContentInteraction;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.object.Report;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.sejong.sulgamewiki.repository.MemberContentInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.repository.ReportRepository;
import org.sejong.sulgamewiki.util.auth.domain.CustomUserDetails;
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
  private final MemberContentInteractionRepository memberContentInteractionRepository;
  private final BaseMediaService baseMediaService;
  private final BaseMediaRepository baseMediaRepository;

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
    member.setCollege(command.getUniversity());
    member.setIsUniversityPublic(command.getIsUniversityVisible());
    member.setIsNotificationEnabled(command.getIsNotiEnabled());
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
    MemberContentInteraction memberContent
        = memberContentInteractionRepository.findByMember(member);
    return MemberDto.builder()
        .member(member)
        .memberContentInteraction(memberContent)
        .build();
  }

  // 관리자 기능
  @Transactional(readOnly = true)
  public MemberDto getReport(MemberCommand command){
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
    MemberContentInteraction memberContent
        = memberContentInteractionRepository.findByMember(member);

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
    MemberContentInteraction memberContent
        = memberContentInteractionRepository.findByMember(member);

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
}
