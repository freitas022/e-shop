package com.myapp.dtos;

import com.myapp.entities.Order;
import com.myapp.entities.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDto {

    private Integer id;
    private Instant moment;
    private OrderStatus orderStatus;
    private ClientDto client;
    private Set<OrderItemDto> items;
    private PaymentDto payment;

    public OrderDto(Order order) {
        id = order.getId();
        moment = order.getMoment();
        orderStatus = order.getOrderStatus();
        client = new ClientDto(order.getClient());
        items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toSet());
        payment = (order.getPayment() == null) ? null : new PaymentDto(order.getPayment());
    }

    public Double getTotal() {
        double sum = 0.0;
        for (OrderItemDto x : items) {
            sum += x.getSubTotal();
        }
        return sum;
    }
}