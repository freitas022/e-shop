package com.myapp.services;

import com.myapp.dtos.OrderDto;
import com.myapp.entities.Order;
import com.myapp.entities.OrderItem;
import com.myapp.entities.enums.OrderStatus;
import com.myapp.repositories.OrderItemRepository;
import com.myapp.repositories.OrderRepository;
import com.myapp.repositories.ProductRepository;
import com.myapp.repositories.UserRepository;
import com.myapp.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	public List<OrderDto> findAll(Integer pageNumber, Integer pageSize, String orderBy, String direction) {
		var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction.toUpperCase()), orderBy);
		var orders = orderRepository.findAll(pageRequest);
		return orders.map(OrderDto::new).stream().toList();
	}

	public OrderDto findById(Long id) {
		return orderRepository.findById(id).map(OrderDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public OrderDto create(OrderDto orderDto) {
		var order = new Order();
		order.setMoment(Instant.now());
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

		var client = userRepository.getReferenceById(orderDto.getClient().getId());
		order.setClient(client);

		orderDto.getItems().forEach(orderItemDto -> {
			var product = productRepository.getReferenceById(orderItemDto.getProductId());
			var item = new OrderItem(order, product, orderItemDto.getQuantity(), product.getPrice());
			order.getItems().add(item);
			product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
		});

		orderRepository.save(order);
		orderItemRepository.saveAll(order.getItems());

		return new OrderDto(order);
	}
}
