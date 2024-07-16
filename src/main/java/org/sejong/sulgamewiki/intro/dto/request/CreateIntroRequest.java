package org.sejong.sulgamewiki.intro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class CreateIntroRequest {
    @Schema(description = "노래의 제목", example = "술게임 시작 노래")
    private String title;

    @Schema(description = "노래에 대한 설명", example = "술게임을 시작할 때 부르는 노래입니다.")
    private String description;

    @Schema(description = "업로드할 파일 목록", type = "array", implementation = MultipartFile.class)
    private List<MultipartFile> multipartFiles;
}
