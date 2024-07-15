package org.sejong.sulgamewiki.admin.dto;

import lombok.Builder;
import org.sejong.sulgamewiki.admin.domain.entity.Admin;

@Builder
public class AdminDto {
    private Long id;
    private String nickname;

    public static AdminDto from(Admin admin){
       return AdminDto.builder()
                .nickname(admin.getNickname())
                .id(admin.getId())
                .build();
    }
}