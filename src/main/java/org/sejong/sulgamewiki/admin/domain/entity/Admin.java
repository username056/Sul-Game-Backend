package org.sejong.sulgamewiki.admin.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sejong.sulgamewiki.common.entity.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


}