package org.sejong.sulgamewiki.intro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.sejong.sulgamewiki.intro.domain.entity.Intro;
import org.sejong.sulgamewiki.intro.dto.response.CreateIntroResponse;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@ToString
public class CreateIntroRequest {
    @Schema(description = "노래의 제목", example = "술게임 시작 노래")
    private String title;

    @Schema(description = "노래에 대한 설명", example = "술게임을 시작할 때 부르는 노래입니다.")
    private String description;

    @Schema(description = "노래 가사", example = "술게임을 시작할 때 부르는 노래가사입니다.")
    private String lyrics;

    public static CreateIntroRequest of(String title, String description, String lyrics) {
        return CreateIntroRequest.builder()
            .title(title)
            .description(description)
            .lyrics(lyrics)
            .build();
    }
}
