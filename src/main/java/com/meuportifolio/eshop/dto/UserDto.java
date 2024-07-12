package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(

    Long id,

    @NotBlank(message = "Cannot be null or empty")
    @Size(min = 3, message = "Must be min 3 characters.")
    String name,

    @NotBlank(message = "Cannot be null or empty")
    @Email(message = "Invalid email.")
    String email,

    @NotBlank(message = "Cannot be null or empty")
    String phone,

    @NotBlank(message = "Cannot be null or empty")
    @Size(min = 8, message = "Must be min 8 characters.")
    String password
    ) {

    public UserDto(User entity) {
        this(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone(), entity.getPassword());
    }
}
