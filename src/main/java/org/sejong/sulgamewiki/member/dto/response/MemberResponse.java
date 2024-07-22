package org.sejong.sulgamewiki.member.dto.response;

import lombok.*;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;

import java.util.List;

@Getter
@Builder
public class MemberResponse {
    private Long id;
    private String university;
    private MemberStatus status;
    private Boolean isNotificationsEnabled;
    private List<String> favoritePopularGames;
    private List<String> favoriteCreativeGames;
    private List<String> favoriteIntroPosts;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .university(member.getUniversity())
                .status(member.getStatus())
                .isNotificationsEnabled(member.getIsNotificationsEnabled())
                .favoritePopularGames(member.getFavoritePopularGames())
                .favoriteCreativeGames(member.getFavoriteCreativeGames())
                .favoriteIntroPosts(member.getFavoriteIntroPosts())
                .build();
    }
}
