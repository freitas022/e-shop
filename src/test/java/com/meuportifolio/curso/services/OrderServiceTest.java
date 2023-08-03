package com.meuportifolio.curso.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
// import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.entities.enums.OrderStatus;
import com.meuportifolio.curso.repositories.OrderRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Order order;
    @Mock
    private User client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new User(1919191919L, "Bob Brown", "bob@gmail.com", "988888888", "123456");
        order = new Order(19191919L, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, client);
    }

    @Test
    void testFindAll() {
        when(orderRepository.findAll()).thenReturn(List.of(order));
        
        List<Order> result = orderService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllEmpty() {        
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<Order> result = orderService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void testFindById() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order actual = orderService.findById(order.getId());

        assertNotNull(actual);
        assertEquals(order.getId(), actual.getId());
        assertEquals(order.getClient(), actual.getClient());
        assertEquals(order.getItems(), actual.getItems());
        assertEquals(order.getMoment(), actual.getMoment());
        assertEquals(order.getOrderStatus(), actual.getOrderStatus());
    }

    @Test
    void testFindByIdNotFound() {
        long id = anyLong();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrowsExactly(ResourceNotFoundException.class, () -> orderService.findById(id));
    }
}