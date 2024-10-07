package com.dot.osore.core.note.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record ViewedDateTime(
        Long number,
        String unit
) {

    public static ViewedDateTime from(LocalDateTime viewedAt) {
        LocalDateTime now = LocalDateTime.now();

        long diff = ChronoUnit.SECONDS.between(viewedAt, now);
        if (diff < 60) {
            return new ViewedDateTime(diff, "seconds");
        }

        diff = ChronoUnit.MINUTES.between(viewedAt, now);
        if (diff < 60) {
            return new ViewedDateTime(diff, "minutes");
        }

        diff = ChronoUnit.HOURS.between(viewedAt, now);
        if (diff < 24) {
            return new ViewedDateTime(diff, "hours");
        }

        diff = ChronoUnit.DAYS.between(viewedAt, now);
        if (diff < 30) {
            return new ViewedDateTime(diff, "days");
        }

        diff = ChronoUnit.MONTHS.between(viewedAt, now);
        if (diff < 12) {
            return new ViewedDateTime(diff, "months");
        }

        diff = ChronoUnit.YEARS.between(viewedAt, now);
        return new ViewedDateTime(diff, "years");
    }
}
