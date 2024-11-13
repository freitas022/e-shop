package com.myapp.services;

import com.myapp.dtos.UserDto;
import com.myapp.entities.User;
import com.myapp.repositories.UserRepository;
import com.myapp.services.exceptions.DatabaseException;
import com.myapp.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	
	public List<UserDto> findAll() {
		return userRepository.findAll().stream().map(UserDto::new).toList();
	}
	
	public UserDto findById(Long id) {
		return userRepository.findById(id).map(UserDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public UserDto insert(User obj) {
		var user = new User(obj.getName(), obj.getPhone(), obj.getEmail(), obj.getPassword());
		userRepository.save(user);
		return new UserDto(user);
	}
	
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public UserDto update(Long id, UserDto obj) {
		try {
		User userFound = userRepository.getReferenceById(id);
		updateData(userFound, obj);
		userRepository.save(userFound);
		return new UserDto(userFound);
		}
		catch (EntityNotFoundException e ) {
			throw new ResourceNotFoundException(id);
		}
		
	}

	private void updateData(User entity, UserDto obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}