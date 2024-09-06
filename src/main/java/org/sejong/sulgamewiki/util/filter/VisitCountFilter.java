package org.sejong.sulgamewiki.util.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.CustomUserDetails;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.MemberInteraction;
import org.sejong.sulgamewiki.repository.MemberInteractionRepository;
import org.sejong.sulgamewiki.repository.MemberRepository;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class VisitCountFilter extends OncePerRequestFilter {

  private final MemberRepository memberRepository;
  private final MemberInteractionRepository memberInteractionRepository;

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

      // 현재 날짜와 비교하여 방문 기록 업데이트
      memberInteraction.incrementDailyVisitCount();
      memberInteractionRepository.save(memberInteraction);
      logger.info("회원 방문 횟수 및 날짜 갱신 완료.");
    }

    // 필터 체인을 계속 진행
    filterChain.doFilter(request, response);
  }
}
