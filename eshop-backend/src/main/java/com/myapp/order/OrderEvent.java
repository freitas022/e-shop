package com.myapp.order;

import com.myapp.consumer.EventType;

public record OrderEvent(OrderDto order, EventType eventType) {
}
