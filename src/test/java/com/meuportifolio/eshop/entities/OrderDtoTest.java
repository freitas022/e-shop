package com.meuportifolio.eshop.entities;

import com.meuportifolio.eshop.dto.OrderDto;
import com.meuportifolio.eshop.dto.OrderItemDto;
import com.meuportifolio.eshop.entities.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDtoTest {

    private OrderDto orderDto;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "Test", "teste@gmail.com", "22999043333", "password");

        Order order = new Order(1L, Instant.now(), OrderStatus.WAITING_PAYMENT, user);

        orderDto = new OrderDto(order);

        Product product = new Product(1L, "Test", "Test description", new BigDecimal(300), "");

        OrderItemDto orderItemDto = new OrderItemDto(product.getId(), product.getName(), product.getPrice(), 1, product.getImgUrl());
        orderDto.getItems().add(orderItemDto);
    }

    @Test
    void test_GetTotal() {
        BigDecimal total = orderDto.getTotal();
        assertEquals(new BigDecimal(300), total);
    }
}