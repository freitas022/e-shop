package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.User;

public record CustomerDto(Long id, String name) {

    public CustomerDto(User entity) {
        this(entity.getId(), entity.getName());
    }
}