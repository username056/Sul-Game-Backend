package org.sejong.sulgamewiki.intro.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.common.entity.BasePost;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.sejong.sulgamewiki.member.domain.entity.Member;

@ToString
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("Intro")
public class Intro extends BasePost {

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
