package org.sejong.sulgamewiki.common.jwt.dto;

public record TokenResponse(
        String refreshToken,
        String accessToken
) {
}
