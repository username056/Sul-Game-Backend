package org.sejong.sulgamewiki.member.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.common.entity.BaseMember;
import org.sejong.sulgamewiki.member.domain.constants.MemberRole;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Member extends BaseMember {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String university;

  private String phone;

  @Column(name = "profile_image_url")
  private String profileImageUrl;

  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  private MemberRole role;

  public void updateUniversity(String university) {
    this.university = university;
  }

  public void updateStatus(MemberStatus status) {
    this.status = status;
  }

  public void updateRole(MemberRole role) {
    this.role = role;
  }
}
