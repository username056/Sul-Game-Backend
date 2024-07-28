package org.sejong.sulgamewiki.member.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.common.entity.BaseMember;
import org.sejong.sulgamewiki.common.entity.constants.MemberRole;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;

import java.util.ArrayList;
import java.util.List;
import org.sejong.sulgamewiki.member.dto.request.CompleteRegistrationRequest;
import org.sejong.sulgamewiki.member.dto.request.CreateMemberRequest;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Member extends BaseMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String university;

  private Boolean isUniversityVisible;

  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  private Boolean isNotiEnabled;

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

  private Integer totalLikes;

  private Boolean infoPopupVisible;

  public static Member toEntity(CreateMemberRequest request) {
    return Member.builder()
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
        .isDeleted(false)
        .build();
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

  public String getNickNameFromBaseMember(){
    return super.getNickname();
  }

  public void updateFromRequest(CompleteRegistrationRequest request) {
    super.setNickname(request.getNickName());
    super.setBirthDate(request.getBirthDate());
    this.university = request.getUniversity();
    this.isUniversityVisible = request.getIsUniversityVisible();
    this.isNotiEnabled = request.getIsNotiEnabled();
  }
}
