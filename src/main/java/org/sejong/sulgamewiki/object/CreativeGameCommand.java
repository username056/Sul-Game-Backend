package org.sejong.sulgamewiki.object;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class CreativeGameCommand {
  private Long memberId;
  private Long basePostId;
  private String title;
  private String description;
  private String introduction;
  private List<String> imageUrls;
  private List<MultipartFile> multipartFiles;
  private List<OfficialGame> relatedOfficialGames;
}
