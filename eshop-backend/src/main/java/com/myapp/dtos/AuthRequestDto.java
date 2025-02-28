package com.myapp.dtos;

import lombok.NonNull;

public record AuthRequestDto(@NonNull String email, @NonNull String password) {
}
