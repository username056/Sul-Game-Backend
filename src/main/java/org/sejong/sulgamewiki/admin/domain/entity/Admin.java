package org.sejong.sulgamewiki.admin.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.admin.dto.AdminDto;
import org.sejong.sulgamewiki.common.entity.BaseMember;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Admin extends BaseMember {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public void updateFromRequest(AdminDto adminDto) {

  }
}