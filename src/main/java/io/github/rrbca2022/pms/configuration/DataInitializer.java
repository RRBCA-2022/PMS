package io.github.rrbca2022.pms.configuration;

import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.entity.PMSConfig;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.repository.PMSConfigRepository;
import io.github.rrbca2022.pms.repository.UserRepository;
import io.github.rrbca2022.pms.utils.PMSLogger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	private final UserRepository userRepository;
	private final PMSConfigRepository configRepository;

	public DataInitializer(UserRepository userRepository, PMSConfigRepository configRepository) {
		this.userRepository = userRepository;
		this.configRepository = configRepository;
	}

	@Bean
	public CommandLineRunner initRootUserAndConfig() {
		return args -> {
			// ===== ROOT USER =====
			if (userRepository.findByUsername("PMS").isEmpty()) {
				User rootUser = new User();
				rootUser.setName("PMS Root");
				rootUser.setEmail("root@example.com");
				rootUser.setUsername("PMS");
				rootUser.setPassword("PMS2026");
				rootUser.setAccountType(AccountType.ADMIN);

				userRepository.save(rootUser);

				PMSLogger.debug("PMS Root User created");
			} else {
				PMSLogger.debug("PMS Root User already exists");
			}

			// ===== PHARMACY CONFIG =====
			if (configRepository.findById(1L).isEmpty()) {
				PMSConfig config = new PMSConfig();
				config.setId(1L); // fixed single-row ID
				config.setPharmacyName("My Pharmacy");
				config.setPharmacyEmail("email@example.com");
				config.setPharmacyAddress("Example Location");

				configRepository.save(config);

				PMSLogger.debug("Pharmacy configuration initialized");
			} else {
				PMSLogger.debug("Pharmacy configuration already exists");
			}
		};
	}
}