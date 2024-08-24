package org.sejong.sulgamewiki.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@ToString
public class OfficialGameCommand {
  private Long memberId;
  private Long basePostId;
  private String title;
  private String description;
  private String introduction;
  private List<String> imageUrls;
  private List<MultipartFile> multipartFiles;
}
