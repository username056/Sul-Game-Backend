package org.sejong.sulgamewiki.util;

import java.util.List;

public record TokenInfo(
        String aud,
        List<String> roles,
        String iat,
        String exp,
        String iss,
        Boolean isValid
) {
}
