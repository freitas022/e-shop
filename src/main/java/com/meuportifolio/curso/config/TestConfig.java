package com.meuportifolio.curso.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.entities.OrderItem;
import com.meuportifolio.curso.entities.Payment;
import com.meuportifolio.curso.entities.Product;
import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.entities.enums.OrderStatus;
import com.meuportifolio.curso.repositories.CategoryRepository;
import com.meuportifolio.curso.repositories.OrderItemRepository;
import com.meuportifolio.curso.repositories.OrderRepository;
import com.meuportifolio.curso.repositories.ProductRepository;
import com.meuportifolio.curso.repositories.UserRepository;

@Configuration
@Profile("dev")
public class TestConfig implements CommandLineRunner {

	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	public TestConfig(UserRepository userRepository, OrderRepository orderRepository,
			CategoryRepository categoryRepository,
			ProductRepository productRepository, OrderItemRepository orderItemRepository) {
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		Category cat1 = new Category(null, "Electronics");
		Category cat2 = new Category(null, "Books");
		Category cat3 = new Category(null, "Computers");

		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", BigDecimal.valueOf(90.50), "");
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", BigDecimal.valueOf(2190.00), "");
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", BigDecimal.valueOf(1250.00), "");
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", BigDecimal.valueOf(1200.00), "");
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", BigDecimal.valueOf(100.99), "");

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


		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.CANCELED, u2);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);

		userRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));

		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

		Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
		Payment pay2 = new Payment(null, Instant.parse("2019-07-21T03:47:20Z"), o2);
		o1.setPayment(pay1);
		o2.setPayment(pay2);

		orderRepository.saveAll(Arrays.asList(o1, o2));
	}
}
