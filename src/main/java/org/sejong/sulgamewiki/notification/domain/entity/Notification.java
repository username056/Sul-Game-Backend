package org.sejong.sulgamewiki.notification.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sejong.sulgamewiki.common.entity.BaseEntity;
import org.sejong.sulgamewiki.member.domain.entity.Member;

import java.time.Instant;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String message;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
