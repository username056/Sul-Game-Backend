package org.sejong.sulgamewiki.object;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@ToString
public class MemberCommand {

  private Long memberId;
  private String nickname;
  private LocalDate birthDate;
  private String university;
  private Boolean isUniversityVisible;
  private Boolean isNotificationEnabled;
  private MultipartFile multipartFile;

  // 경험치 계산
  private Integer expRank; // 경험치 순위
  private Double expRankPercentile; // 경험치 상위 몇 퍼센트인지
  private Long nextLevelExp; // 다음 레벨까지 필요한 경험치
  private Long remainingExpForNextLevel; // 다음 레벨까지 남은 경험치
  private Double progressPercentToNextLevel; // 다음 레벨까지의 진행 퍼센트
}
