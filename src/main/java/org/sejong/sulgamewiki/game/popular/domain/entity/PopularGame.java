package org.sejong.sulgamewiki.game.popular.domain.entity;

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
import org.sejong.sulgamewiki.game.popular.dto.request.CreatePopularGameRequest;
import org.sejong.sulgamewiki.member.domain.entity.Member;

@ToString
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("PopularGame")
public class PopularGame extends BasePost {

  @Column(length = 90)
  private String introduction;

  public static PopularGame toEntity(Member member, CreatePopularGameRequest request) {
    return PopularGame.builder()
        .title(request.getDescription())
        .description(request.getDescription())
        .member(member)
        .likes(0)
        .views(0)
        .build();
  }
}