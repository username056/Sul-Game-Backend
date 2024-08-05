package org.sejong.sulgamewiki.intro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.sejong.sulgamewiki.intro.domain.entity.Intro;

@Getter
@Builder
@ToString
public class CreateIntroResponse {
    private Long introId;
    private Long memberId;
    private String title;
    private String description;

    public static CreateIntroResponse from(Intro intro) {
        return CreateIntroResponse.builder()
                .introId(intro.getId())
                .memberId(intro.getMember().getId())
                .title(intro.getTitle())
                .description(intro.getDescription())
                .build();
    }
}
