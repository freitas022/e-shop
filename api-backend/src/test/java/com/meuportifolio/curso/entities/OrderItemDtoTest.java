package com.meuportifolio.curso.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

import com.meuportifolio.curso.dto.OrderItemDto;

public class OrderItemDtoTest {
    
    @Test
    void test_GetSubTotal() {

        BigDecimal valueOfProduct = BigDecimal.valueOf(230.15);

        Product p1 = new Product(1111L, "Mouse Gamer", "Lorem ipsum dollor.", valueOfProduct, "");

        OrderItemDto orderItemDto = new OrderItemDto(p1.getId(), p1.getName(), p1.getPrice(), 3, p1.getImgUrl());

        BigDecimal total = orderItemDto.getSubTotal();
        assertEquals(new BigDecimal(690.45).setScale(2, RoundingMode.HALF_EVEN), total);
    }
}
