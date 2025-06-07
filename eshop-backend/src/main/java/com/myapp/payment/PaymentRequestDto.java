package com.myapp.payment;

import com.myapp.order.Order;

public record PaymentRequestDto(
        Integer orderId,
        Long clientId,
        Double total
) {
    public PaymentRequestDto(Order order) {
        this(order.getId(), order.getClient().getId(), order.getTotal());
    }
}
