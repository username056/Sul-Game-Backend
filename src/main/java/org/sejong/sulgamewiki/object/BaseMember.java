package org.sejong.sulgamewiki.object;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import org.sejong.sulgamewiki.object.constants.MemberRole;


@ToString
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseMember extends BaseTimeEntity {


    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private LocalDate birthDate;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    private MemberRole role;
}
