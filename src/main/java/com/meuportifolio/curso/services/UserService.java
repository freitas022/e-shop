package com.meuportifolio.curso.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.repositories.UserRepository;
import com.meuportifolio.curso.services.exceptions.DatabaseException;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private final UserRepository repository;

	public UserService(UserRepository userRepository) {
		this.repository = userRepository;
	}

	public List<User> findAll() {
		LOGGER.info("Searching all users.");
		return repository.findAll();
	}

	public User findById(Long id) {
		LOGGER.info("Searching user by id.");
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User obj) {
		LOGGER.info("Creating new user.");
		return repository.save(obj);
	}

	public User update(Long id, User obj) {
		LOGGER.info("Updating an existing user.");
		return repository.findById(id).map(user -> {
			updateData(user, obj);
			return repository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPassword(obj.getPassword());
		entity.setPhone(obj.getPhone());
	}
}
