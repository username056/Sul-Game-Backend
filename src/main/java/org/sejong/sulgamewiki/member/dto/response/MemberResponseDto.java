package org.sejong.sulgamewiki.member.dto.response;

import lombok.*;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String university;
    private MemberStatus status;
    private Boolean isNotificationsEnabled;
    private List<String> favoritePosts;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .university(member.getUniversity())
                .status(member.getStatus())
                .isNotificationsEnabled(member.getIsNotificationsEnabled())
                .favoritePosts(member.getFavoritePosts())
                .build();
    }
}
