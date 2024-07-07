package org.sejong.sulgamewiki.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.common.entity.constants.MediaType;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class BaseMedia extends BaseEntity {

  @Column(nullable = false)
  private String mediaUrl;

  @Column(nullable = false)
  private Long fileSize;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MediaType mediaType;
}
