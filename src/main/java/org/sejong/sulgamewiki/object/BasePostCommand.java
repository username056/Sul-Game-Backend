package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.service.OfficialGameService;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@ToString
public class BasePostCommand {
  private Long basePostId;
  private Long memberId;
  private SourceType sourceType;
  private BasePost basePost;

  private String introduction;
  private String title;
  private String description;
  private List<MultipartFile> multipartFiles;
}
