package org.sejong.sulgamewiki.object;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sejong.sulgamewiki.object.constants.RankChangeStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyMemberRanking extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private Member member;

  private LocalDate recordDate;

  private Integer rank;

  private Long exp;

  @Enumerated(EnumType.STRING)
  private RankChangeStatus rankChangeStatus;

  private Integer rankChange;

  @Column(nullable = false)
  private LocalDateTime updateTime; // 업데이트 시간
}
