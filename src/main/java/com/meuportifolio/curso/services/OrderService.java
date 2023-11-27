package com.meuportifolio.curso.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.repositories.OrderRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	private final OrderRepository repository;

	public OrderService(OrderRepository orderRepository) {
		this.repository = orderRepository;
	}

	public List<Order> findAll() {
		return repository.findAll();
	}

	public Order findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
