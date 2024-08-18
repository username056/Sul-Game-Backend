package org.sejong.sulgamewiki.member.object;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column
  private LocalDateTime birthDate;

  @Column
  private String profileUrl;

  @Enumerated(EnumType.STRING)
  private Role role = Role.ROLE_USER;

  @Column
  private String college = "정보 없음";

  @Column(nullable = false)
  private Boolean isUniversityPublic = true;

  @Enumerated(EnumType.STRING)
  private AccountStatus accountStatus = AccountStatus.PENDING;

  @Column(nullable = false)
  private Boolean isNotificationEnabled = true;

  @Column(nullable = false)
  private Boolean infoPopupVisible = true;

  @Column(nullable = false)
  private LocalDateTime lastLoginTime;
}