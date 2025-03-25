package com.myapp.cart;

import com.myapp.user.ClientDto;

import java.util.List;

public record CartDto(Long id, ClientDto client, List<CartItemDto> items) {

    public CartDto(Cart cart) {
        this(cart.getId(), new ClientDto(cart.getUser()), cart.getItems().stream().map(CartItemDto::new).toList());
    }

    public Double getTotal() {
        return items.stream()
                .mapToDouble(CartItemDto::getSubTotal)
                .sum();
    }
}
