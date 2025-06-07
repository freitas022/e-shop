package com.myapp.consumer;

import com.myapp.payment.PaymentRequestDto;
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

    @SqsListener(value = "order-placed-queue", maxConcurrentMessages = "10", maxMessagesPerPoll = "5")
    public void handlePaymentRequest(PaymentRequestDto paymentRequest) throws InterruptedException {
        log.info("Received payment request: {}", paymentRequest);
        paymentService.processPayment(paymentRequest);
    }
}

