package org.sejong.sulgamewiki.member.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.common.entity.BaseMember;
import org.sejong.sulgamewiki.common.entity.constants.MemberRole;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;

import java.util.ArrayList;
import java.util.List;
import org.sejong.sulgamewiki.member.dto.request.CreateMemberRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Member extends BaseMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String university;

  // 근데 이거 유니버시티가 필수가 아닌데 얘는 필수면 어캄. 확인하는거 하나 필요할듯
  @Column(nullable = false)
  private Boolean isUniversityVisible;

  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  @Builder.Default
  @Column(nullable = false)
  private Boolean isNotiEnabled = false;

  @ElementCollection
  @CollectionTable(name = "member_popular_game_stars", joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "post_id")
  @Builder.Default
  private List<String> favoritePopularGames = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "member_creative_game_stars", joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "post_id")
  @Builder.Default
  private List<String> favoriteCreativeGames = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "member_intro_stars", joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "post_id")
  @Builder.Default
  private List<String> favoriteIntroPosts = new ArrayList<>();

  @Column(nullable = false)
  private Integer totalLikes = 0;

  @Column(nullable = false)
  private Boolean infoPopupVisible = true;

  public static Member toEntity(CreateMemberRequest request) {
    return Member.builder()
        .email(request.getEmail())
        .nickname(request.getNickName())
        .birthDate(request.getBirthDate())
        .profileImageUrl(null)
        .role(MemberRole.ROLE_USER)
        .university(request.getUniversity())
        .isUniversityVisible(request.getIsUniversityVisible())
        .status(MemberStatus.ACTIVE)
        .isNotiEnabled(request.getIsNotiEnabled())
        .favoritePopularGames(new ArrayList<>())
        .favoriteCreativeGames(new ArrayList<>())
        .favoriteIntroPosts(new ArrayList<>())
        .totalLikes(0)
        .infoPopupVisible(true)
        .build();
  }

  public void updateUniversity(String university) {
    this.university = university;
  }
  public void updateStatus(MemberStatus status) {
    this.status = status;
  }

  public void setNotificationStatus(Boolean isNotificationsEnabled) {
    this.isNotiEnabled = isNotificationsEnabled;
  }

  public void addFavoritePopularGame(String postId) {
    favoritePopularGames.add(postId);
  }

  public void removeFavoritePopularGame(String postId) {
    favoritePopularGames.remove(postId);
  }

  public void addFavoriteCreativeGame(String postId) {
    favoriteCreativeGames.add(postId);
  }

  public void removeFavoriteCreativeGame(String postId) {
    favoriteCreativeGames.remove(postId);
  }

  public void addFavoriteIntroPost(String postId) {
    favoriteIntroPosts.add(postId);
  }

  public void removeFavoriteIntroPost(String postId) {
    favoriteIntroPosts.remove(postId);
  }

}
