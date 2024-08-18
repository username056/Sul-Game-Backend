package org.sejong.sulgamewiki.common.object;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@ToString
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseMember extends BaseEntity {


    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private LocalDate birthDate;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    private MemberRole role;
}
