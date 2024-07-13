package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.Category;

public record CategoryDto(Long id, String name) {

    public CategoryDto(Category entity) {
        this(entity.getId(), entity.getName());
    }
}
