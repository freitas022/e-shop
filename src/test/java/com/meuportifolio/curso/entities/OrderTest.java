package com.meuportifolio.curso.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.meuportifolio.curso.entities.enums.OrderStatus;

/**
 * OrderTest
 */
class OrderTest {

    @Test
    void test_GetTotal() {
        
        BigDecimal valor1 = BigDecimal.valueOf(230.15);
        BigDecimal valor2 = BigDecimal.valueOf(30.45);
        
        Product p1 = new Product(1111L, "Mouse Gamer", "Lorem ipsum dollor.", valor1, "");
        Product p2 = new Product(2222L, "Mouse Convencional", "Lorem ipsum.", valor2, "");
        
        Order order = new Order(System.currentTimeMillis(), Instant.now(), OrderStatus.WAITING_PAYMENT, null);
        

        OrderItem item = new OrderItem(order, p1, 3, p1.getPrice());
        order.getItems().add(item);
        
        OrderItem item2 = new OrderItem(order, p2, 3, valor2);
        order.getItems().add(item2);        

        BigDecimal total = order.getTotal();
        assertEquals(new BigDecimal(781.80).setScale(2, RoundingMode.HALF_EVEN), total);
    }
}