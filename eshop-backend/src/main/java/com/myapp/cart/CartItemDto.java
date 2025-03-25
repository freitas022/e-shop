package com.myapp.cart;

import com.myapp.product.ProductDto;

public record CartItemDto(ProductDto product, Integer quantity) {

    public CartItemDto(CartItem entity) {
        this(new ProductDto(entity.getId().getProduct()), entity.getQuantity());
    }

    public Double getSubTotal() {
        return product.getPrice() * quantity;
    }
}
