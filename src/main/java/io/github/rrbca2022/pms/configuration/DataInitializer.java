package io.github.rrbca2022.pms.configuration;

import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.entity.PMSConfig;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.services.PMSConfigService;
import io.github.rrbca2022.pms.services.UserService;
import io.github.rrbca2022.pms.utils.PMSLogger;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer {
	private final UserService userService;
	private final PMSConfigService configService;

	@EventListener(ApplicationReadyEvent.class)
	public void initializeData() {
		// ===== ROOT USER =====
		if (!userService.adminExists()) {
			User rootUser = new User();
			rootUser.setName("PMS Root");
			rootUser.setEmail("root@example.com");
			rootUser.setUsername("PMS");
			rootUser.setPassword("PMS2026");
			rootUser.setAccountType(AccountType.ADMIN);

			userService.saveUser(rootUser);
			PMSLogger.debug("PMS Root User created");
		} else {
			PMSLogger.debug("PMS Root / Admin User already exists");
		}

		// ===== PHARMACY CONFIG =====
		if (!configService.doesConfigExists()) {
			PMSConfig config = new PMSConfig();
			config.setId(1L); // fixed single-row ID
			config.setPharmacyName("My Pharmacy");
			config.setPharmacyEmail("email@example.com");
			config.setPharmacyAddress("Example Location");
			config.setCurrencySymbol("$");
			configService.saveConfig(config); // saves + updates cache
			PMSLogger.debug("Pharmacy configuration initialized");
		} else {
			PMSLogger.debug("Pharmacy configuration already exists");
		}
	}
}
