package com.myapp.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto(

        @NotBlank
        @Size(min = 3, max = 50, message = "Enter a name between 3 and 50 characters")
        String name,

        @NotBlank
        @Email(message = "Enter a valid email address")
        String email,

        @NotBlank
        @Size(min = 8, max = 64, message = "Enter a password between 8 and 64 characters")
        String password,

        @NotBlank
        @Pattern(regexp = "^[1-9]{2}9\\d{8}$", message = "Invalid Brazilian cellphone format")
        String phone
) {}