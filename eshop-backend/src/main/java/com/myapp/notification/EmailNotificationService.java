package com.myapp.notification;

import com.myapp.order.OrderDto;
import com.myapp.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final EmailService emailService;

    public void sendWelcomeEmail(UserDto user) {
        var message = EmailTemplateFactory.createWelcomeMessage(user);
        emailService.send(message);
    }

    public void notifyOrderConfirmation(OrderDto order) {
        var message = EmailTemplateFactory.notifyOrderConfirmation(order);
        emailService.send(message);
    }

    public void notifyOrderUpdate(OrderDto order) {
        var message = EmailTemplateFactory.createOrderUpdateMessage(order);
        emailService.send(message);
    }

    public void notifyPaymentProcessed(OrderDto order) {
        var message = EmailTemplateFactory.createPaymentProcessedMessage(order);
        emailService.send(message);
    }
}
