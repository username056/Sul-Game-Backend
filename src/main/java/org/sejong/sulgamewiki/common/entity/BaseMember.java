package org.sejong.sulgamewiki.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.common.entity.constants.MemberRole;

import java.time.LocalDate;


@SuperBuilder
@Getter
@Setter
@ToString(exclude = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class BaseMember extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate birthDate;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    private MemberRole role;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    
    public void updateRole(MemberRole role) {
        this.role = role;
    }
}
