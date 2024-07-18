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

  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  @Column(nullable = false)
  private Boolean isNotificationsEnabled = true;

  // TODO: 대학교 공개 비공개
  @ElementCollection
  @CollectionTable(name = "member_stars", joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "post_id")
  private List<String> favoritePosts = new ArrayList<>();

  public void updateUniversity(String university) {
    this.university = university;
  }
  public void updateStatus(MemberStatus status) {
    this.status = status;
  }

  public void setNotificationStatus(Boolean isNotificationsEnabled) {
    this.isNotificationsEnabled = isNotificationsEnabled;
  }

  public void addFavoritePost(String postId) {
    favoritePosts.add(postId);
  }

  public void removeFavoritePost(String postId) {
    favoritePosts.remove(postId);
  }

}
