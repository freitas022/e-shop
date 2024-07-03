package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.OrderDto;
import com.meuportifolio.eshop.dto.OrderItemDto;
import com.meuportifolio.eshop.entities.Order;
import com.meuportifolio.eshop.entities.Product;
import com.meuportifolio.eshop.entities.User;
import com.meuportifolio.eshop.entities.enums.OrderStatus;
import com.meuportifolio.eshop.repositories.OrderItemRepository;
import com.meuportifolio.eshop.repositories.OrderRepository;
import com.meuportifolio.eshop.repositories.ProductRepository;
import com.meuportifolio.eshop.repositories.UserRepository;
import com.meuportifolio.eshop.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private Order order;
    private OrderDto orderDto;
    private OrderItemDto orderItemDto;
    private Product product;
    @Mock
    private User client;

    @BeforeEach
    void setUp() {
        product = new Product(123L, "Teste", "Teste", BigDecimal.valueOf(500), "");
        client = new User(1919191919L, "Bob Brown", "bob@gmail.com", "988888888", "123456");
        order = new Order(1L, Instant.parse("2024-06-20T19:53:07Z"), OrderStatus.WAITING_PAYMENT, client);
        orderItemDto = new OrderItemDto(product.getId(), product.getName(), product.getPrice(), 1, product.getImgUrl());
        orderDto = new OrderDto(order);
    }

    @Test
    void testFindAll() {
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderDto> result = orderService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllEmpty() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<OrderDto> result = orderService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void testFindById() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        OrderDto actual = orderService.findById(order.getId());

        assertNotNull(actual);
        assertEquals(order.getId(), actual.getId());
        assertEquals(order.getClient().getId(), actual.getCustomer().id());
        assertEquals(order.getMoment(), actual.getMoment());
        assertEquals(order.getOrderStatus(), actual.getOrderStatus());
    }

    @Test
    void testFindByIdNotFound() {
        long id = anyLong();
        when(orderRepository.findById(id)).thenThrow(ResourceNotFoundException.class);

        assertThrowsExactly(ResourceNotFoundException.class, () -> orderService.findById(id));
    }

    @Test
    void createNewOrder() {
        orderDto.getItems().add(orderItemDto);

        when(userRepository.getReferenceById(orderDto.getCustomer().id())).thenReturn(client);
        when(productRepository.getReferenceById(123L)).thenReturn(product);

        var newOrder = orderService.createOrder(orderDto);

        assertNotNull(newOrder);
        assertEquals(OrderStatus.WAITING_PAYMENT, newOrder.getOrderStatus());

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(anySet());
    }
}