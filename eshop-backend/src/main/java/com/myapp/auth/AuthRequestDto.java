package com.myapp.auth;

import lombok.NonNull;

public record AuthRequestDto(@NonNull String email, @NonNull String password) {
}
