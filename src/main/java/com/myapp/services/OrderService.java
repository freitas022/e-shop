package com.myapp.services;

import com.myapp.dtos.OrderDto;
import com.myapp.repositories.OrderRepository;
import com.myapp.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepository;

	public List<OrderDto> findAll() {
		return orderRepository.findAll().stream().map(OrderDto::new).toList();
	}

	public OrderDto findById(Long id) {
		return orderRepository.findById(id).map(OrderDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
