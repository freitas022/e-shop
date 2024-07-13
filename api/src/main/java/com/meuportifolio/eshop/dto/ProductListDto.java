package com.meuportifolio.eshop.dto;

import java.util.List;

public record ProductListDto(
        List<ProductMinDto> products,
        int page,
        int pageSize,
        long totalElements
) {
}
