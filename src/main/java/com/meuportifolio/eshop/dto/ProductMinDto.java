package com.meuportifolio.eshop.dto;

import java.math.BigDecimal;

public record ProductMinDto (
        Long id,
        String name,
        String description,
        BigDecimal price
) {
}
