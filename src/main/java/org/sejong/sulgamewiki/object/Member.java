package org.sejong.sulgamewiki.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.sejong.sulgamewiki.object.constants.Role;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //트러블슈팅 참고
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
  private String university = "정보 없음";

  @Builder.Default
  @Column(nullable = false)
  private Boolean isUniversityPublic = true;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AccountStatus accountStatus = AccountStatus.PENDING;

  @Column(nullable = false)
  private String provider; // 로그인 제공자 (google, kakao, naver)

  @Builder.Default
  @Column(nullable = false)
  private Boolean isNicknameModified = false;

  @Builder.Default
  @Column(nullable = false)
  private Boolean isProfileImageModified = false;

  @Builder.Default
  @Column(nullable = false)
  private Boolean isNotificationEnabled = true;

  @Builder.Default
  @Column(nullable = false)
  private Boolean infoPopupVisible = true;

  // 마지막 로그인 일시
  @Builder.Default
  private LocalDateTime lastLoginTime = LocalDateTime.now();

  private String refreshToken;

  public void setNickname(String name) {
    if (!isNicknameModified) {
      this.nickname = name;
    }
  }

  public void setProfileUrl(String profileImageUrl) {
    if (!isProfileImageModified) {
      this.profileUrl = profileImageUrl;
    }
  }
}