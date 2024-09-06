package org.sejong.sulgamewiki.util.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.CustomUserDetails;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.object.constants.ExpRule;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.service.ExpManagerService;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class VisitCountFilter extends OncePerRequestFilter {

  private final MemberRepository memberRepository;
  private final MemberInteractionRepository memberInteractionRepository;
  private final ExpManagerService expManagerService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // JWT 필터에서 인증된 사용자 정보 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Authentication 이 존재하는 경우
    if (authentication != null && authentication.isAuthenticated()) {
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      Long memberId = userDetails.getMember().getMemberId();

      // Member와 MemberContentInteraction 정보 조회
      Member member = memberRepository.findById(memberId)
          .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
      MemberInteraction memberInteraction = memberInteractionRepository.findByMember(member)
          .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_INTERACTION_NOT_FOUND));

      LocalDate today = LocalDate.now();

      // 첫 로그인 확인 및 포인트 부여
      if (memberInteraction.getLastVisitDate() == null || !memberInteraction.getLastVisitDate().equals(today)) {
        memberInteraction.upDailyVisitCount();
        memberInteraction.setLastVisitDate(today);
        memberInteractionRepository.save(memberInteraction);

        // 첫 로그인 시 포인트 부여
        expManagerService.updateExp(member, ExpRule.LOGIN_FIRST_TIME);
      }
      log.info("회원 {}: 방문 기록 갱신 완료", member.getEmail());
    }

    // 필터 체인을 계속 진행
    filterChain.doFilter(request, response);
  }
}
