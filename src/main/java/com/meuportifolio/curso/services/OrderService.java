package com.meuportifolio.curso.services;

import java.util.List;

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

	public OrderService(OrderRepository orderRepository) {
		this.repository = orderRepository;
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
}
