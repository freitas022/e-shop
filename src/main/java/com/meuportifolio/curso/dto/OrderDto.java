package com.meuportifolio.curso.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.entities.enums.OrderStatus;


public class OrderDto {
    
    private Long id;
    private Instant moment;
    private OrderStatus orderStatus;
    private CustomerDto customer; 
    private Set<OrderItemDto> items = new HashSet<>(); 
    private PaymentDto payment;

    public OrderDto() {}

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