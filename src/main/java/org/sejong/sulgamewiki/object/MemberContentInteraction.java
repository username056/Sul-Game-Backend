package org.sejong.sulgamewiki.object;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MemberContentInteraction extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(nullable = false)
  private Integer totalLikeCount = 0;

  @Column(nullable = false)
  private Integer totalCommentCount = 0;

  @Column(nullable = false)
  private Integer totalPostCount = 0;

  @Column(nullable = false)
  private Integer totalCommentLikeCount = 0;

  @Column(nullable = false)
  private Integer totalPostLikeCount = 0;

  @Column(nullable = false)
  private Integer totalMediaCount = 0;

  @ElementCollection
  private List<Long> likedOfficialGameIds = new ArrayList<>();

  @ElementCollection
  private List<Long> likedCreationGameIds = new ArrayList<>();

  @ElementCollection
  private List<Long> likedIntroIds = new ArrayList<>();

  @ElementCollection
  private List<Long> bookmarkedOfficialGameIds = new ArrayList<>();

  @ElementCollection
  private List<Long> bookmarkedCreationGameIds = new ArrayList<>();

  @ElementCollection
  private List<Long> bookmarkedIntroIds = new ArrayList<>();
}