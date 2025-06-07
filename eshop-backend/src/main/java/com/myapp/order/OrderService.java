package com.myapp.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.cart.CartService;
import com.myapp.exceptions.CartEmptyException;
import com.myapp.exceptions.ResourceNotFoundException;
import com.myapp.exceptions.StockInsufficientException;
import com.myapp.payment.PaymentRequestDto;
import com.myapp.product.ProductRepository;
import com.myapp.sqs.SqsService;
import com.myapp.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${sqs.cloud.aws.queue-name}")
	private String queueName;


	public List<OrderDto> findAll(Integer pageNumber, Integer pageSize, String orderBy, String direction) {
		var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction.toUpperCase()), orderBy);
		var orders = orderRepository.findAll(pageRequest);
		return orders.map(OrderDto::new).stream().toList();
	}

	public OrderDto findById(Long id) {
		return orderRepository.findById(id).map(OrderDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public OrderDto create(OrderDto orderDto) throws JsonProcessingException {
		var order = new Order();
		order.setMoment(Instant.parse(fmt.format(now)));
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

		var client = userRepository.getReferenceById(orderDto.getClient().getId());
		order.setClient(client);

		orderDto.getItems().forEach(orderItemDto -> {
			var product = productRepository.getReferenceById(orderItemDto.getProductId());
			if(product.getStockQuantity() < orderItemDto.getQuantity()) {
				throw new StockInsufficientException(product.getId());
			}
			var item = new OrderItem(order, product, orderItemDto.getQuantity(), product.getPrice());
			order.getItems().add(item);
			product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
		});

		orderRepository.save(order);
		orderItemRepository.saveAll(order.getItems());

		var paymentRequest = new PaymentRequestDto(order);
		var message = objectMapper.writeValueAsString(paymentRequest);
		sqsService.sendMessage(queueName, message);

		return new OrderDto(order);
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
			if(product.getStockQuantity() < cartItem.getQuantity()) {
				throw new StockInsufficientException(product.getId());
			}

			product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
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
