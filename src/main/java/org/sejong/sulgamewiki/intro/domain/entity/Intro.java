package org.sejong.sulgamewiki.intro.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.common.object.BasePost;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.sejong.sulgamewiki.member.object.Member;

@ToString
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Intro extends BasePost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @Column(length = 200)
  private String lyrics;
  public static Intro toEntity(Member member, CreateIntroRequest request) {
    return Intro.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .lyrics(request.getLyrics())
        .member(member)
        .likes(0)
        .views(0)
        .build();
  }
}
