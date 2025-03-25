package com.myapp.cart;

import com.myapp.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Entity(name = "tb_cart_item")
public class CartItem {

    @EmbeddedId
    private CartItemPK id = new CartItemPK();
    private Integer quantity;

    public CartItem(Cart cart, Product product, int quantity) {
        id.setCart(cart);
        id.setProduct(product);
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return id.getProduct().getPrice() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
