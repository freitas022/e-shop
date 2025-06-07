package com.myapp.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.exceptions.ResourceNotFoundException;
import com.myapp.order.*;
import com.myapp.sqs.SqsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final SqsService sqsService;
    private final ObjectMapper objectMapper;

    @Transactional
    public void processPayment(Integer orderId) throws JsonProcessingException {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(orderId));
        var payment = new Payment();

        payment.setMoment(Instant.now());
        payment.setOrder(order);

        order.setPayment(payment);
        order.setOrderStatus(OrderStatus.PAID);

        log.info("Payment processed successfully for order: {}", order.getId());
        sendOrderClosedEvent(order);
    }

    private void sendOrderClosedEvent(Order order) throws JsonProcessingException {
        var dto = new OrderDto(order);
        var message = objectMapper.writeValueAsString(new OrderEvent(dto));
        sqsService.sendMessage("order-closed-queue", message);
    }

}
