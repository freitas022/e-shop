package com.meuportifolio.eshop.entities;

import com.meuportifolio.eshop.dto.OrderItemDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemDtoTest {

    @Test
    void test_GetSubTotal() {

        BigDecimal valueOfProduct = BigDecimal.valueOf(230.15);

        Product p1 = new Product(1111L, "Mouse Gamer", "Lorem ipsum dollor.", valueOfProduct, "");

        OrderItemDto orderItemDto = new OrderItemDto(p1.getId(), p1.getName(), p1.getPrice(), 3, p1.getImgUrl());

        BigDecimal total = orderItemDto.getSubTotal();

        assertEquals(new BigDecimal("690.45"), total);
    }
}