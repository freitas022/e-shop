package com.meuportifolio.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.repositories.UserRepository;
import com.meuportifolio.curso.services.exceptions.DatabaseException;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.delete(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public User update(Long id, User obj) {
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
