package com.myapp.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.cart.CartService;
import com.myapp.consumer.EventType;
import com.myapp.exceptions.CartEmptyException;
import com.myapp.exceptions.ResourceNotFoundException;
import com.myapp.product.ProductRepository;
import com.myapp.sqs.SqsService;
import com.myapp.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

	private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC);
	private static final Instant now = Instant.now();

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;
	private final CartService cartService;
	private final SqsService sqsService;
	private final ObjectMapper objectMapper;

	public List<OrderDto> findAll(Integer pageNumber, Integer pageSize, String orderBy, String direction) {
		var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction.toUpperCase()), orderBy);
		var orders = orderRepository.findAll(pageRequest);
		return orders.map(OrderDto::new).stream().toList();
	}

	public OrderDto findById(Integer id) {
		return orderRepository.findById(id).map(OrderDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public OrderDto create(OrderDto orderDto) {
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		order.setClient(userRepository.getReferenceById(orderDto.getClient().getId()));

		for (OrderItemDto itemDto : orderDto.getItems()) {
			var product = productRepository.getReferenceById(itemDto.getProductId());
			order.getItems().add(new OrderItem(order, product, itemDto.getQuantity(), product.getPrice()));
		}

		orderRepository.save(order);

		publishEvent(order);

		return new OrderDto(order);
	}

    /**
     *
     * @param order
     *
     * dispatch event to the SQS queue when an order is created.
     */
	private void publishEvent(Order order) {
		try {
			OrderDto dto = new OrderDto(order);
			String message = objectMapper.writeValueAsString(new OrderEvent(dto, EventType.ORDER_CREATED));
			sqsService.sendMessage("payment-queue", message);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to serialize order event", e);
		}
	}

	@Transactional
	public OrderDto createFromCart(String email) {
		var cart = cartService.getCartByUser(email);
		if (cart.getItems().isEmpty()) throw new CartEmptyException();
		var order = new Order();
		order.setClient(cart.getUser());
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		order.setMoment(Instant.parse(fmt.format(now)));

		cart.getItems().forEach(cartItem -> {
			var product = cartItem.getId().getProduct();

			productRepository.save(product);

			var orderItem =  new OrderItem(order, product, cartItem.getQuantity(), product.getPrice());
			order.getItems().add(orderItem);
		});

		orderRepository.save(order);
		orderItemRepository.saveAll(order.getItems());
		cartService.clearCart(cart.getUser().getEmail());

		return new OrderDto(order);
	}
}
