package org.sejong.sulgamewiki.admin.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sejong.sulgamewiki.common.entity.BaseMember;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admin")
//@ToString(callSuper = true, exclude = "password")
public class Admin extends BaseMember {



}