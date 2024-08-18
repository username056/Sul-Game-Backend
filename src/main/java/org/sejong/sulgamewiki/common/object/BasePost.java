package org.sejong.sulgamewiki.common.object;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.member.object.Member;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public abstract class BasePost extends BaseEntity {

  @Column(length = 100)
  private String title;

  @Column(length = 500)
  private String description;

  @Builder.Default
  private int likes = 0;

  @Builder.Default
  private int views = 0;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;
}