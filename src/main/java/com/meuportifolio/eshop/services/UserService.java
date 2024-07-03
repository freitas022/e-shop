package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.UserDto;
import com.meuportifolio.eshop.entities.User;
import com.meuportifolio.eshop.repositories.UserRepository;
import com.meuportifolio.eshop.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        LOGGER.info("user - find all");
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .toList();
    }

    public UserDto findById(Long id) {
        LOGGER.info("user - find by id.");
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public UserDto insert(UserDto userDto) {
        LOGGER.info("user - insert new user.");
        var user = new User();
        updateData(user, userDto);
        userRepository.save(user);
        return new UserDto(user);
    }

    public UserDto update(Long id, UserDto dto) {
        LOGGER.info("user - update user.");
        return userRepository.findById(id).map(user -> {
            updateData(user, dto);
            userRepository.save(user);
            return new UserDto(user);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private void updateData(User entity, UserDto dto) {
        LOGGER.info("user - update data method");
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        entity.setPhone(dto.phone());
    }
}
