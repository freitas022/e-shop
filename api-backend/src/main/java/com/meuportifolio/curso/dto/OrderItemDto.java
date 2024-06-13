package com.meuportifolio.curso.dto;

import java.math.BigDecimal;

import com.meuportifolio.curso.entities.OrderItem;

public record OrderItemDto(Long productId, String name, BigDecimal price, Integer quantity, String imgUrl) {

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
