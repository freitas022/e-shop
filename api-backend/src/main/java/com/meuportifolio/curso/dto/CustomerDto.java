package com.meuportifolio.curso.dto;

import com.meuportifolio.curso.entities.User;

public record CustomerDto(Long id, String name) {

    public CustomerDto(User entity) {
        this(entity.getId(), entity.getName());
    }
}