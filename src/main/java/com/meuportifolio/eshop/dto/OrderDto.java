package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.Order;
import com.meuportifolio.eshop.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderDto {

    private final Long id;
    private final Instant moment;
    private final OrderStatus orderStatus;
    private final CustomerDto customer;
    private Set<OrderItemDto> items = new HashSet<>();
    private final PaymentDto payment;

    public OrderDto(Long id, Instant moment, OrderStatus orderStatus, CustomerDto customer, PaymentDto payment) {
        this.id = id;
        this.moment = moment;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.payment = payment;
    }

    public OrderDto(Order entity) {
        this.id = entity.getId();
        this.moment = entity.getMoment();
        this.orderStatus = entity.getOrderStatus();
        this.customer = new CustomerDto(entity.getClient().getId(), entity.getClient().getName());
        this.items = entity.getItems().stream().map(OrderItemDto::new).collect(Collectors.toSet());
        this.payment = (entity.getPayment() == null) ? null : new PaymentDto(entity.getPayment());
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public Set<OrderItemDto> getItems() {
        return items;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(OrderItemDto::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}