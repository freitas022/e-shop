package com.myapp.user;

import com.myapp.consumer.EventType;

public record UserEvent(UserDto user, EventType eventType) {
}
