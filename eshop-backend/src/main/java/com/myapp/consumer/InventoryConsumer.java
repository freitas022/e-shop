package com.myapp.consumer;

import com.myapp.inventory.InventoryService;
import com.myapp.order.OrderEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryConsumer {

    private final InventoryService inventoryService;

    @SqsListener("order-closed-queue")
    public void handleOrderClosed(OrderEvent event) {
        log.info("Processing inventory update for order: {}", event.order().getId());
        var order = event.order();
        inventoryService.decreaseStock(order);
    }
}

