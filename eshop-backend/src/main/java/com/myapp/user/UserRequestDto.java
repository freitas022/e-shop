package com.myapp.user;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(@NotBlank String name, @NotBlank String email, @NotBlank String password, @NotBlank String phone) {
}
