package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.User;

public record UserDto(Long id, String name, String email, String phone, String password) {

        public UserDto(User entity) {
            this(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone(), entity.getPassword());
        }
}
