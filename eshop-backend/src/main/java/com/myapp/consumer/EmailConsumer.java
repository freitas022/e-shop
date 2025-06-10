package com.myapp.consumer;

import com.myapp.notification.EmailNotificationService;
import com.myapp.order.OrderEvent;
import com.myapp.user.UserEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailNotificationService notificationService;

    @SqsListener(value = "user-queue")
    public void handleUserEvents(UserEvent event) {
        switch (event.eventType()) {
            case USER_CREATED -> {
                log.info("Sending welcome email to: {}", event.user().getEmail());
                notificationService.sendWelcomeEmail(event.user());
            }
            case USER_UPDATED -> {
                log.info("Sending update email to: {}", event.user().getEmail());
            }
        }
    }

    @SqsListener(value = "order-queue")
    public void handleOrderEvents(OrderEvent event) {
        switch (event.eventType()) {
            case ORDER_CREATED -> {
                log.info("Sending order confirmation to id: # {}", event.order().getClient().getEmail());
                notificationService.notifyOrderConfirmation(event.order());
            }
            case ORDER_UPDATED -> {
                log.info("Sending order update to id: # {}", event.order().getId());
                notificationService.notifyOrderUpdate(event.order());
            }
            case PAYMENT_PROCESSED -> {
                log.info("Sending payment confirmation to id: # {}", event.order().getClient().getEmail());
                notificationService.notifyPaymentProcessed(event.order());
            }
        }
    }
}