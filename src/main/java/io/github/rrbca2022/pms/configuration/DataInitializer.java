package io.github.rrbca2022.pms.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.repository.UserRepository;

@Configuration
public class DataInitializer {

	private final UserRepository userRepository;

	public DataInitializer(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Bean
	public CommandLineRunner initRootUser() {
		return args -> {
			if (userRepository.findByUsername("root").isEmpty()) {
				User rootUser = new User();
				rootUser.setName("Root User");
				rootUser.setEmail("root@example.com");
				rootUser.setUsername("root");
				rootUser.setPassword("PMS2026");
				rootUser.setAccountType(AccountType.ADMIN);

				userRepository.save(rootUser);

				System.out.println("Root admin user created!");
			} else {
				System.out.println("Root admin already exists.");
			}
		};
	}
}