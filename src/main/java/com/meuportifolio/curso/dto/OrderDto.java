package com.meuportifolio.curso.dto;

import java.time.Instant;
import java.util.Set;

import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.entities.OrderItem;
import com.meuportifolio.curso.entities.Payment;
import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.entities.enums.OrderStatus;

/**
 * OrderDto
 */
public record OrderDto(Long id, Instant moment, OrderStatus orderStatus, User client, Set<OrderItem> items, Payment payment) {

    public OrderDto(Order entity) {
        this(entity.getId(), entity.getMoment(), entity.getOrderStatus(), entity.getClient(), entity.getItems(), entity.getPayment());
    }
}