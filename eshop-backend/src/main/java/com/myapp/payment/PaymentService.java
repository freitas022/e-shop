package com.myapp.payment;

import com.myapp.order.OrderRepository;
import com.myapp.order.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    @Transactional
    public void processPayment(PaymentRequestDto paymentRequest) throws InterruptedException {
        var orderId = Long.valueOf(paymentRequest.orderId());

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + orderId));

        Thread.sleep(7000);

        var payment = new Payment();
        payment.setMoment(Instant.now());
        payment.setOrder(order);

        order.setPayment(payment);
        order.setOrderStatus(OrderStatus.PAID);

        log.info("Payment processed successfully for order: {}", paymentRequest.orderId());
    }

}
