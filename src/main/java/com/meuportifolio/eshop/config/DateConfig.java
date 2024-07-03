package com.meuportifolio.eshop.config;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateConfig {

    private static final DateTimeFormatter DATETIME_FORMAT_UTC = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private DateConfig() {
        throw new IllegalStateException("Utility class");
    }

    public static Instant formatDate(Instant instant) {
        return Instant.parse(
                DATETIME_FORMAT_UTC
                        .format(instant.atZone(
                                ZoneId.systemDefault()
                        )));
    }
}
