package org.sejong.sulgamewiki.object;

import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@ToString
public class MemberCommand {
  private Long memberId;
  private String nickname;
  private LocalDate birthDate;
  private String university;
  private Boolean isUniversityVisible;
  private Boolean isNotificationEnabled;
  private Optional<MultipartFile> multipartFileOptional;
}
