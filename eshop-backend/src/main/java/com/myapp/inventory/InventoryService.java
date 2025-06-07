package com.myapp.inventory;

import com.myapp.exceptions.ResourceNotFoundException;
import com.myapp.exceptions.StockInsufficientException;
import com.myapp.order.OrderDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public void decreaseStock(OrderDto order) {
        order.getItems().forEach(item -> {
            var inventory =  inventoryRepository.findByProductId(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(item.getProductId()));

            if (inventory.getQuantity() < item.getQuantity()) {
                throw new StockInsufficientException(item.getProductId());
            }

            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
            inventoryRepository.save(inventory);
        });

    }
}
