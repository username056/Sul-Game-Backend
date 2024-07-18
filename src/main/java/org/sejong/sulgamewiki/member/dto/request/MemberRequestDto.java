package org.sejong.sulgamewiki.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;

import java.util.List;

@Getter
@Builder
public class MemberRequestDto {
    private Long id;
    private String university;
    private MemberStatus status;
    private Boolean isNotificationsEnabled;
    private List<String> favoritePosts;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .university(university)
                .status(status)
                .isNotificationsEnabled(isNotificationsEnabled)
                .favoritePosts(favoritePosts)
                .build();
    }
}
