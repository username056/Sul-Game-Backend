package org.sejong.sulgamewiki.object;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.object.constants.AccountStatus;
import org.sejong.sulgamewiki.object.constants.ExpLevel;
import org.sejong.sulgamewiki.object.constants.Role;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Member extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column
  private LocalDate birthDate;

  @Column
  private String profileUrl;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private Role role = Role.ROLE_USER;

  @Builder.Default
  @Column
  private String college = "정보 없음";

  @Builder.Default
  @Column(nullable = false)
  private Long exp = 0L;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private ExpLevel expLevel = ExpLevel.D;

  @Builder.Default
  @Column(nullable = false)
  private Boolean isUniversityPublic = true;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AccountStatus accountStatus = AccountStatus.PENDING;

  @Builder.Default
  @Column(nullable = false)
  private Boolean isNotificationEnabled = true;

  @Builder.Default
  @Column(nullable = false)
  private Boolean infoPopupVisible = true;

  @Column(nullable = false)
  private LocalDateTime lastLoginTime;
}