package com.myapp.consumer;

import java.util.Arrays;

public enum EventType {

    USER_CREATED,
    USER_UPDATED,
    ORDER_CREATED,
    PAYMENT_PROCESSED,
    ORDER_UPDATED;

    @Override
    public String toString() {
        return formatEnumName(this.name());
    }

    private static String formatEnumName(String raw) {
        String[] parts = raw.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();

        Arrays.stream(parts).forEach(part -> {
            if (!part.isEmpty()) {
                result.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1))
                        .append(" ");
            }
        });
        return result.toString().trim();
    }
}
