package com.meuportifolio.curso.services;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import com.meuportifolio.curso.dto.OrderDto;
import com.meuportifolio.curso.entities.OrderItem;
import com.meuportifolio.curso.entities.enums.OrderStatus;
import com.meuportifolio.curso.repositories.OrderItemRepository;
import com.meuportifolio.curso.repositories.ProductRepository;
import com.meuportifolio.curso.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.repositories.OrderRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository repository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	public OrderService(OrderRepository orderRepository, UserRepository userRepository,
						ProductRepository productRepository,
						OrderItemRepository orderItemRepository) {
		this.repository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderItemRepository;
	}

	public List<Order> findAll() {
		LOGGER.info("Searching all orders.");
		return repository.findAll();
	}

	public Order findById(Long id) {
		LOGGER.info( "Searching order by id.");
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public OrderDto createOrder(OrderDto orderDto) {
		var order = new Order();
		Instant moment = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
		order.setMoment(moment);
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		var user = userRepository.findById(orderDto.getCustomer().id());
		order.setClient(user.get());
		orderDto.getItems().forEach(orderItem -> {
			var product = productRepository.getReferenceById(orderItem.productId());
			OrderItem item = new OrderItem(order, product, orderItem.quantity(), product.getPrice());
			order.getItems().add(item);
		});
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		return new OrderDto(order);
	}
}
