package org.sejong.sulgamewiki.notification.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorSource {
    SQL_FILE("sql_file", "SQL 오류"),
    SQL("sql", "SQL 오류"),
    AUTH("auth", "인증 오류"),
    LOG("log", "로그 오류"),
    OTHERS("others", "정의되지 않은 오류");

    private final String source;
    private final String description;
}