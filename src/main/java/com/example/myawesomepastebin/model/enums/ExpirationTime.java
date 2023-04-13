package com.example.myawesomepastebin.model.enums;

import java.time.temporal.ChronoUnit;

public enum ExpirationTime {
    TEN_MIN(10L, ChronoUnit.MINUTES),
    ONE_HOUR(1L, ChronoUnit.HOURS),
    THREE_HOURS(3L, ChronoUnit.HOURS),
    ONE_DAY(1L, ChronoUnit.DAYS),
    ONE_WEEK(1L, ChronoUnit.WEEKS),
    ONE_MONTH(1L, ChronoUnit.MONTHS),
    FOREVER(Long.MAX_VALUE, ChronoUnit.FOREVER);
    private  Long time;
    private ChronoUnit chronoUnit;

    ExpirationTime(Long time, ChronoUnit chronoUnit) {
    }
}
