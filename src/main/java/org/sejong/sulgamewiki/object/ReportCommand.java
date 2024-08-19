package org.sejong.sulgamewiki.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.SourceType;

@Getter
@Setter
@Builder
@ToString
public class ReportCommand {
  private Long memberId;
  private SourceType sourceType;
  private Long sourceId;
  private Long reportType;
}
