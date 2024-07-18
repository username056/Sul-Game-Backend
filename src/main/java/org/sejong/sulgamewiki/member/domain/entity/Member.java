package org.sejong.sulgamewiki.member.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.common.entity.BaseMember;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;

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



  // TODO: 즐겨찾는 게시물 목록


  public void updateUniversity(String university) {
    this.university = university;
  }
  public void updateStatus(MemberStatus status) {
    this.status = status;
  }

}
