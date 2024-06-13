package com.meuportifolio.curso.dto;

import com.meuportifolio.curso.entities.Category;

public record CategoryDto(Long id, String name) {

    public CategoryDto(Category entity) {
        this(entity.getId(), entity.getName());
    }
}
