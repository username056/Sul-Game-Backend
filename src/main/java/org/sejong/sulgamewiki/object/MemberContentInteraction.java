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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class MemberContentInteraction extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private Member member;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalLikeCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalCommentCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalPostCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalCommentLikeCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalPostLikeCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer totalMediaCount = 0;

  @Builder.Default
  @ElementCollection
  private List<Long> likedOfficialGameIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> likedCreationGameIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> likedIntroIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> bookmarkedOfficialGameIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> bookmarkedCreationGameIds = new ArrayList<>();

  @Builder.Default
  @ElementCollection
  private List<Long> bookmarkedIntroIds = new ArrayList<>();
}