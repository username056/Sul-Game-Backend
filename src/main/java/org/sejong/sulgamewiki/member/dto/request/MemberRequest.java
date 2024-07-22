package org.sejong.sulgamewiki.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;

import java.util.List;

@Getter
@Builder
public class MemberRequest {
    private Long id;
    private String university;
    private MemberStatus status;
    private Boolean isNotificationsEnabled;
    private Boolean isUniversityPublic;
    private List<String> favoritePopularGames;
    private List<String> favoriteCreativeGames;
    private List<String> favoriteIntroPosts;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .university(university)
                .isUniversityPublic(isUniversityPublic)
                .status(status)
                .isNotificationsEnabled(isNotificationsEnabled)
                .favoritePopularGames(favoritePopularGames)
                .favoriteCreativeGames(favoriteCreativeGames)
                .favoriteIntroPosts(favoriteIntroPosts)
                .build();
    }

    public boolean isUniversityPublic() {
        return isUniversityPublic;
    }
}
