package com.meuportifolio.curso.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.repositories.UserRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private User expected;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		expected = new User(1919191919L, "Bob Brown", "bob@gmail.com", "988888888", "123456");
	}

	@Test
	void testDelete() {
		doNothing().when(userRepository).delete(expected);
		userRepository.delete(expected);
		verify(userRepository, times(1)).delete(any(User.class));
	}

	@Test
	void testDeleteNotFound() {
		long id = 2325120000L;
		doThrow(new ResourceNotFoundException(id)).when(userRepository).delete(any());
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));

		assertThrows(ResourceNotFoundException.class, () -> userService.delete(id));

		verify(userRepository).findById(anyLong());
		verify(userRepository).delete(any());
	}

	@Test
	void testFindAll() {
		when(userRepository.findAll()).thenReturn(List.of(expected));

		List<User> actual = userService.findAll();

		assertNotNull(actual);
		assertEquals(1, actual.size());
	}

	@Test
	void testFindById() {
		when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

		User actual = userService.findById(expected.getId());

		assertNotNull(actual);
		assertEquals(1919191919L, actual.getId());
		assertEquals("Bob Brown", actual.getName());
		assertEquals(User.class, actual.getClass());
	}

	@Test
	void testFindByIdNotFound() {
		long id = 123L;
		when(userRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.findById(id));

		verify(userRepository).findById(anyLong());
		verify(userRepository, times(1)).findById(id);
	}

	@Test
	void testInsert() {
		when(userRepository.save(any(User.class))).thenReturn(expected);

		User actual = userService.insert(expected);

		assertNotNull(actual);
		assertEquals(1919191919L, actual.getId());
		assertEquals("Bob Brown", actual.getName());
		assertEquals(User.class, actual.getClass());
	}

	@Test
	void testUpdate() {
		User updated = new User(1919191919L, "Alex Green", "alex@gmail.com", "12121293", "passw0rd");

		when(userRepository.findById(updated.getId())).thenReturn(Optional.of(expected));
		when(userRepository.save(expected)).thenReturn(updated);

		User actual = userService.update(1919191919L, updated);
		verify(userRepository).findById(anyLong());

		assertEquals("Alex Green", actual.getName());
		assertEquals(updated, actual);
	}

	@Test
	void testUpdateNotFound() {
		long id = 12345L;
		User updated = new User(id, "Alex Green", "alex@gmail.com", "12121293", "passw0rd");

		when(userRepository.findById(id)).thenThrow(new ResourceNotFoundException(id));

		assertThrows(ResourceNotFoundException.class, () -> userService.update(id, updated));
		verify(userRepository).findById(anyLong());
		verify(userRepository, never()).save(updated);
	}
}
