package org.sejong.sulgamewiki.member.dto.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPresentationDto  {
    private Long id;
    private String university;
    private MemberStatus status;
    private Boolean isNotificationsEnabled;
    private List<String> favoritePosts;
    public static MemberPresentationDto from(Member member) {
        return new MemberPresentationDto(
                member.getId(),
                member.getUniversity(),
                member.getStatus(),
                member.getIsNotificationsEnabled(),
                member.getFavoritePosts()
        );
    }
}
