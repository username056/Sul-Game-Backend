package org.sejong.sulgamewiki.member.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.common.entity.BaseMember;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;

import java.util.ArrayList;
import java.util.List;
import org.sejong.sulgamewiki.member.dto.request.MemberRequest;

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

  @Column(nullable = false)
  private boolean isUniversityPublic;

  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  @Column(nullable = false)
  private Boolean isNotificationsEnabled = true;

  @ElementCollection
  @CollectionTable(name = "member_popular_game_stars", joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "post_id")
  private List<String> favoritePopularGames = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "member_creative_game_stars", joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "post_id")
  private List<String> favoriteCreativeGames = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "member_intro_stars", joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "post_id")
  private List<String> favoriteIntroPosts = new ArrayList<>();

  public void updateUniversity(String university) {
    this.university = university;
  }
  public void updateStatus(MemberStatus status) {
    this.status = status;
  }

  public void setNotificationStatus(Boolean isNotificationsEnabled) {
    this.isNotificationsEnabled = isNotificationsEnabled;
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


  public void updateFromRequest(MemberRequest memberRequest) {
    this.university = memberRequest.getUniversity();
    this.isUniversityPublic = memberRequest.isUniversityPublic();
    this.status = memberRequest.getStatus();
    this.isNotificationsEnabled = memberRequest.getIsNotificationsEnabled();
    this.favoritePopularGames = new ArrayList<>(memberRequest.getFavoritePopularGames());
    this.favoriteCreativeGames = new ArrayList<>(memberRequest.getFavoriteCreativeGames());
    this.favoriteIntroPosts = new ArrayList<>(memberRequest.getFavoriteIntroPosts());
  }

}
