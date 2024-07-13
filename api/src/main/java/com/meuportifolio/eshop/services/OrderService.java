package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.OrderDto;
import com.meuportifolio.eshop.entities.Order;
import com.meuportifolio.eshop.entities.OrderItem;
import com.meuportifolio.eshop.entities.enums.OrderStatus;
import com.meuportifolio.eshop.repositories.OrderItemRepository;
import com.meuportifolio.eshop.repositories.OrderRepository;
import com.meuportifolio.eshop.repositories.ProductRepository;
import com.meuportifolio.eshop.repositories.UserRepository;
import com.meuportifolio.eshop.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	public OrderService(OrderRepository orderRepository, UserRepository userRepository,
						ProductRepository productRepository,
						OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderItemRepository;
	}

	public List<OrderDto> findAll() {
		LOGGER.info("order - find all");
		return orderRepository.findAll()
				.stream()
				.map(OrderDto::new)
				.toList();
	}

	public OrderDto findById(Long id) {
		LOGGER.info( "order - find by id");
		return orderRepository.findById(id)
				.map(OrderDto::new)
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Transactional
	public OrderDto createOrder(OrderDto orderDto) {
		// obtain user
		var user = userRepository.findById(orderDto.getCustomer().id())
				.orElseThrow(ResourceNotFoundException::new);

		// build order
		var order = new Order();
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		order.setClient(user);

        orderDto.getItems().forEach(orderItem -> {
			// find the product
			var product = productRepository.findById(orderItem.productId())
					.orElseThrow(ResourceNotFoundException::new);
			// obtain items of orderDto
			var item = new OrderItem(order, product, orderItem.quantity(), product.getPrice());
			// add items to order list
			order.getItems().add(item);
		});
		LOGGER.info("order - insert");
		orderRepository.save(order);
		orderItemRepository.saveAll(order.getItems());
		return new OrderDto(order);
	}
}
