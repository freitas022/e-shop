package com.meuportifolio.eshop.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(

        @NotBlank(message = "Cannot be null or empty.")
        String email,

        @NotBlank(message = "Cannot be null or empty.")
        String password) {
}
