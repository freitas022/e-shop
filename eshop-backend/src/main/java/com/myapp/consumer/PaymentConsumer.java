package com.myapp.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myapp.order.OrderEvent;
import com.myapp.payment.PaymentService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;

    @SqsListener(value = "payment-queue", maxConcurrentMessages = "10", maxMessagesPerPoll = "5")
        public void handlePaymentRequest(OrderEvent event) throws JsonProcessingException {
        log.info("Received payment request: {}", event.order().getId());
        var order = event.order();
        paymentService.processPayment(order.getId());
    }
}

