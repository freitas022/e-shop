package com.meuportifolio.curso.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.meuportifolio.curso.dto.OrderDto;
import com.meuportifolio.curso.dto.OrderItemDto;
import com.meuportifolio.curso.entities.enums.OrderStatus;

class OrderDtoTest {

    @Test
    void test_GetTotal() {

        BigDecimal valueOfProduct = BigDecimal.valueOf(230.15);

        Product p1 = new Product(1111L, "Mouse Gamer", "Lorem ipsum dollor.", valueOfProduct, "");
        User customer = new User(12345L, "User", "user@mail.com", "null", "null");

        Order order = new Order(System.currentTimeMillis(), Instant.now(), OrderStatus.WAITING_PAYMENT, customer);
        OrderDto orderDto = new OrderDto(order);
        OrderItem item = new OrderItem(order, p1, 3, p1.getPrice());

        OrderItemDto orderItemDto = new OrderItemDto(item);
        orderDto.getItems().add(orderItemDto);

        BigDecimal total = orderDto.getTotal();
        assertEquals(new BigDecimal(690.45).setScale(2, RoundingMode.HALF_EVEN), total);
    }
}