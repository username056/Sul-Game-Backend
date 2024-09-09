package org.sejong.sulgamewiki.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.SortBy;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

@Builder
@Getter
@Setter
@ToString
public class HomeCommand {
  private int pageNumber;
  private int pageSize;
  private Pageable pageable;
  private SourceType postType;
  private SortBy sortBy;
  private Direction Direction;
}
