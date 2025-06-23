package com.myapp.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.auth.Role;
import com.myapp.consumer.EventType;
import com.myapp.exceptions.DatabaseException;
import com.myapp.exceptions.ResourceNotFoundException;
import com.myapp.order.OrderDto;
import com.myapp.sqs.SqsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ObjectMapper objectMapper;
	private final SqsService sqsService;

	public List<UserDto> findAll(Integer pageNumber, Integer pageSize, String orderBy, String direction) {
		var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction.toUpperCase()), orderBy);
		var users = userRepository.findAll(pageRequest);
		return users.map(UserDto::new).stream().toList();
	}

	public UserDto findById(Long id) {
		return userRepository.findById(id).map(UserDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public void insert(UserRequestDto obj) throws JsonProcessingException {
		var userCreated = new User(obj.name(), obj.phone(), obj.email(), passwordEncoder.encode(obj.password()), Role.CUSTOMER);
		userRepository.save(userCreated);
		var user = new UserDto(userCreated);
		var msg = objectMapper.writeValueAsString(new UserEvent(user, EventType.USER_CREATED));
		sqsService.sendMessage("user-queue", msg);
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
		var user = new UserDto(userFound);
		var msg = objectMapper.writeValueAsString(new UserEvent(user, EventType.USER_UPDATED));
		sqsService.sendMessage("user-queue", msg);
		return user;
		}
		catch (EntityNotFoundException e ) {
			throw new ResourceNotFoundException(id);
		} catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

	private void updateData(User entity, UserDto obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}

	public List<OrderDto> findOrdersByUserId(Long id) {
		var user =  userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return user.getOrders().stream().map(OrderDto::new).toList();
	}

    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDto::new)
                .orElseThrow(() -> new ResourceNotFoundException(email));
    }
}
