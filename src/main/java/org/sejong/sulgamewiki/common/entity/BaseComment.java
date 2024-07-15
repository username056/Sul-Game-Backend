package org.sejong.sulgamewiki.common.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sejong.sulgamewiki.member.domain.entity.Member;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class BaseComment extends BaseEntity {

    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
