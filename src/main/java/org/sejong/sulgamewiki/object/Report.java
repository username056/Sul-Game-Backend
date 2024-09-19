package org.sejong.sulgamewiki.object;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.object.constants.ReportStatus;
import org.sejong.sulgamewiki.object.constants.ReportType;
import org.sejong.sulgamewiki.object.constants.SourceType;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Report extends BaseTimeEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reportId;


  @ManyToOne
  private Member reporter; // 신고자

  private SourceType sourceType; // 신고된 게시물 소스타입 TODO: 이름 나중에 바꾸기

  private Long sourceId;   // 신고된 객체의 아이디값

  private ReportType reportType; // 신고 사유

  private ReportStatus status;
}