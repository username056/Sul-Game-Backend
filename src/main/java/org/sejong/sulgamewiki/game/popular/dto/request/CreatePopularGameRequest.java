package org.sejong.sulgamewiki.game.popular.dto.request;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class CreatePopularGameRequest {
  private String title;
  private String description;
  private ArrayList<MultipartFile> multipartFiles;
}
