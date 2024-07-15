package org.sejong.sulgamewiki.game.popular.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class CreatePopularGameRequest {
  @Schema(description = "게임의 제목", example = "딸기당근수박참외메론 게임")
  private String title;

  @Schema(description = "게임에 대한 설명", example = "딸기당근수박참외 메론~ 게임! 딸기당근수박참외 메론~ 게임!")
  private String description;

  @Schema(description = "업로드할 파일 목록", type = "array", implementation = MultipartFile.class)
  private List<MultipartFile> multipartFiles;
}
