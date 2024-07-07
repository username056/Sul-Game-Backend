package org.sejong.sulgamewiki.common.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sejong.sulgamewiki.member.domain.entity.Member;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class BaseGame extends BaseEntity {

  @Column(nullable = false, length = 100)
  private String title;

  @Column(length = 500)
  private String description;

  @Column(nullable = false)
  private int likes;

  @Column(nullable = false)
  private int views;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;
}
