package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.UserDto;
import com.meuportifolio.eshop.entities.User;
import com.meuportifolio.eshop.repositories.UserRepository;
import com.meuportifolio.eshop.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private UserDto userDto;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 111111133333L;
        user = new User(1L, "Bob Brown", "bob@gmail.com", "988888888", "12345678");
        userDto = new UserDto(user);
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> actualList = userService.findAll();

        assertEquals(1, actualList.size());
        assertEquals("Bob Brown", actualList.getFirst().name());
    }


    @Test
    void testFindById() {
        when(userRepository.findById(existingId)).thenReturn(Optional.of(user));

        UserDto actual = userService.findById(existingId);

        assertNotNull(actual);
        assertEquals(existingId, actual.id());
        assertEquals("Bob Brown", actual.name());
        assertEquals(UserDto.class, actual.getClass());
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(nonExistingId));
    }

    @Test
    void testInsert() {
        when(userRepository.save(user)).thenReturn(user);

        UserDto actual = userService.insert(userDto);

        assertNotNull(userDto);
        assertEquals(actual.name(), user.getName());
        assertEquals(actual.email(), user.getEmail());
        assertEquals(actual.password(), user.getPassword());
        assertEquals(actual.phone(), user.getPhone());
    }


    @Test
    void testUpdate() {
        UserDto userToUpdate = new UserDto(existingId, "Alex Green", "alex@gmail.com", "12121293", "passw0rd");

        when(userRepository.findById(existingId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserDto actual = userService.update(existingId, userToUpdate);

        assertEquals("Alex Green", actual.name());
        assertEquals(userToUpdate.id(), actual.id());
    }

    @Test
    void testUpdateNotFound() {

        UserDto jsonBody = new UserDto(nonExistingId, "Alex Green", "alex@gmail.com", "22912121293", "passw0rd");

        when(userRepository.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userService.update(nonExistingId, jsonBody));
    }
}
