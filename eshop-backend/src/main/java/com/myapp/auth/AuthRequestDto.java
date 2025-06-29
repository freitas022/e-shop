package com.myapp.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AuthRequestDto(

        @NotEmpty(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,

        @NotEmpty(message = "Password is mandatory")
        String password
) {}
