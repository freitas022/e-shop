package com.myapp.cart;

public record AddToCartRequestDto(Long productId, int quantity) {
}
