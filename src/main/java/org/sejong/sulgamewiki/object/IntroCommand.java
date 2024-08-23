package org.sejong.sulgamewiki.object;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.constants.IntroType;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@ToString
public class IntroCommand {
  private Long memberId;
  private String title;
  private String description;
  private String lyrics;
  private IntroType type;
  private List<MultipartFile> multipartFiles;
}
