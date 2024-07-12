package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.OrderItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemDto(

        Long productId,
        String name,
        BigDecimal price,

        @NotNull(message = "Cannot be null or empty.")
        @Positive(message = "More than 0")
        Integer quantity,

        String imgUrl) {

    public OrderItemDto(OrderItem entity) {
        this(entity.getProduct().getId(),
             entity.getProduct().getName(),
             entity.getProduct().getPrice(),
             entity.getQuantity(),
             entity.getProduct().getImgUrl());
    }

    public BigDecimal getSubTotal() {
        return this.price.multiply(new BigDecimal(quantity));
    }
}
