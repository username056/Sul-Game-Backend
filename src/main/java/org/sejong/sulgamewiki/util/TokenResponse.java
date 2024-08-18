package org.sejong.sulgamewiki.util;

public record TokenResponse(
        String refreshToken,
        String accessToken
) {
}
