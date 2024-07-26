package org.sejong.sulgamewiki.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;

import java.util.List;

@Getter
@Builder
public class MemberDto {
    private Long id;
    private String university;
    private MemberStatus status;
    private Boolean isNotificationsEnabled;
    private Boolean isUniversityVisible;
    private List<String> favoritePopularGames;
    private List<String> favoriteCreativeGames;
    private List<String> favoriteIntroPosts;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .university(university)
                .isUniversityVisible(isUniversityVisible())
                .status(status)
                .isNotificationsEnabled(isNotificationsEnabled)
                .favoritePopularGames(favoritePopularGames)
                .favoriteCreativeGames(favoriteCreativeGames)
                .favoriteIntroPosts(favoriteIntroPosts)
                .build();
    }

    public boolean isUniversityVisible() {
        return isUniversityVisible;
    }
}
