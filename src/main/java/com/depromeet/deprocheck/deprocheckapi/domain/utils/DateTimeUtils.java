package com.depromeet.deprocheck.deprocheckapi.domain.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateTimeUtils {
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");

    private DateTimeUtils() {

    }

    public static LocalDate parseDate(String date) {
        if (StringUtils.isEmpty(date)) {
            throw new IllegalArgumentException("'date' must not be null or empty");
        }
        Matcher matcher = DATE_PATTERN.matcher(date);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("'date' is not supported. expected pattern is 'yyyy-mm-dd'");
        }
        return LocalDate.of(
                Integer.valueOf(matcher.group(1)),
                Integer.valueOf(matcher.group(2)),
                Integer.valueOf(matcher.group(3))
        );
    }
}
