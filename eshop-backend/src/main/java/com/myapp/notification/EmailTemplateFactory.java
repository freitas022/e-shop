package com.myapp.notification;

import com.myapp.order.OrderDto;
import com.myapp.user.UserDto;

public record EmailTemplateFactory() {

    public static MessageSender createWelcomeMessage(UserDto user) {
        String subject = "Welcome to E-shop!";
        String body = String.format(
                """
                        Hello %s,
                        
                        Thank you for registering with E-shop! We are excited to have you on board.
                        
                        Best regards,
                        E-shop Team""", user.getName());
        return new MessageSender(user.getEmail(), subject, body);
    }

    public static MessageSender notifyOrderConfirmation(OrderDto order) {
        String subject = "Order confirmed.";
        String body = "Dear " + order.getClient().getName()
                + ", thank you for placing order. Your order number is "
                + order.getId()
                + ". We will notify you once your order is shipped.";
        return new MessageSender(order.getClient().getEmail(), subject, body);
    }

    public static MessageSender createOrderUpdateMessage(OrderDto order) {
        String subject = "Order updated.";
        String body = "Your order was updated. Order number: "
                + order.getId() + " Status: "
                + order.getOrderStatus().toString();
        return new MessageSender(order.getClient().getEmail(), subject, body);
    }

    public static MessageSender createPaymentProcessedMessage(OrderDto order) {
        String subject = "Payment processed.";
        String body = String.format("Your payment for order # %s has been successfully processed.", order.getId());
        return new MessageSender(order.getClient().getEmail(), subject, body);
    }
}
