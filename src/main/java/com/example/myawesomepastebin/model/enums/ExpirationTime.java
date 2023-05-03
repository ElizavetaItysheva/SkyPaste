package com.example.myawesomepastebin.model.enums;

import java.time.temporal.ChronoUnit;

public enum ExpirationTime {
    TEN_MIN(10L, ChronoUnit.MINUTES),
    ONE_HOUR(1L, ChronoUnit.HOURS),
    THREE_HOURS(3L, ChronoUnit.HOURS),
    ONE_DAY(1L, ChronoUnit.DAYS),
    ONE_WEEK(1L, ChronoUnit.WEEKS),
    ONE_MONTH(1L, ChronoUnit.MONTHS),
    FOREVER(73_000L, ChronoUnit.DAYS);
    private final Long time;
    private final ChronoUnit chronoUnit;

    ExpirationTime(Long time, ChronoUnit chronoUnit) {
        this.time = time;
        this.chronoUnit = chronoUnit;
    }
    public Long getTime() {
        return time;
    }
    public ChronoUnit getChronoUnit() {
        return chronoUnit;
    }
}
