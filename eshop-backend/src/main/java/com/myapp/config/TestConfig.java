package com.myapp.config;

import com.myapp.entities.*;
import com.myapp.entities.enums.OrderStatus;
import com.myapp.entities.enums.Role;
import com.myapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category("Electronics");
		Category cat2 = new Category("Books");
		Category cat3 = new Category("Computers");
		
		Product p1 = new Product("The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "", 100);
		Product p2 = new Product("Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "", 100);
		Product p3 = new Product("Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "", 100);
		Product p4 = new Product("PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "", 100);
		Product p5 = new Product("Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "", 100);
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		productRepository.saveAll(Arrays.asList(p1, p2 , p3, p4, p5));
		
		p1.getCategories().add(cat2);
		p2.getCategories().add(cat1);
		p2.getCategories().add(cat3);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);
		
		//salvar produtos
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		
		User u1 = new User("Maria Brown", "988888888", "maria@gmail.com", passwordEncoder.encode( "password"), Role.CUSTOMER);
		User u2 = new User("Alex Green", "977777777", "alex@gmail.com", passwordEncoder.encode( "password"), Role.ADMIN);
		
		Order o1 = new Order(Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
		Order o2 = new Order(Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.SHIPPED, u2);
		Order o3 = new Order(Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);
		Order o4 = new Order(Instant.parse("2025-01-22T23:54:37Z"), OrderStatus.CANCELED, u2);
		Order o5 = new Order(Instant.parse("2025-01-22T23:55:34Z"), OrderStatus.DELIVERED, u2);
		Order o6 = new Order(Instant.parse("2024-11-28T18:51:02Z"), OrderStatus.DELIVERED, u1);
					
		userRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3, o4, o5, o6));
		
		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());
		OrderItem oi5 = new OrderItem(o4, p4, 1, p4.getPrice());
		OrderItem oi6 = new OrderItem(o5, p2, 2, p2.getPrice());
		OrderItem oi7 = new OrderItem(o6, p2, 2, p2.getPrice());
		
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4, oi5, oi6, oi7));
		
		Payment pay1 = new Payment(Instant.parse("2019-06-20T21:53:07Z"), o1);
		Payment pay2 = new Payment(Instant.parse("2019-07-21T03:43:21Z"), o2);
		Payment pay5 = new Payment(Instant.parse("2025-01-22T23:58:29Z"), o5);
		Payment pay6 = new Payment(Instant.parse("2024-11-28T18:57:02Z"), o6);

		o1.setPayment(pay1);
		o2.setPayment(pay2);
		o5.setPayment(pay5);
		o6.setPayment(pay6);
		
		orderRepository.saveAll(List.of(o1, o2, o5, o6));
	}
}
