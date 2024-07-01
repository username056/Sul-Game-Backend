package org.sejong.sulgamewiki.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sejong.sulgamewiki.common.entity.BaseEntity;
import org.sejong.sulgamewiki.member.domain.constants.MemberRole;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;

@ToString(exclude = "password")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  private LocalDate birthDate;

  private String university;

  private String phone;

  @Column(name = "profile_image_url")
  private String profileImageUrl;

  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  private MemberRole role;

  public Member(String nickname, String email, String password, LocalDate birthDate, String university, String profileImageUrl, String phone, MemberStatus status, MemberRole role) {
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.birthDate = birthDate;
    this.university = university;
    this.profileImageUrl = profileImageUrl;
    this.phone = phone;
    this.status = status;
    this.role = role;
  }

  public void updateNickname(String nickname) {
    this.nickname = nickname;
  }

  public void updateEmail(String email) {
    this.email = email;
  }

  public void updatePassword(String password) {
    this.password = password;
  }

  public void updateBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public void updateUniversity(String university) {
    this.university = university;
  }

  public void updateProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public void updatePhone(String phone) {
    this.phone = phone;
  }

  public void updateStatus(MemberStatus status) {
    this.status = status;
  }

  public void updateRole(MemberRole role) {
    this.role = role;
  }
}
