package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.PMSConfig;
import io.github.rrbca2022.pms.repository.PMSConfigRepository;
import io.github.rrbca2022.pms.utils.PMSLogger;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor()
public class PMSConfigService {
	private final PMSConfigRepository pmsConfigRepository;
	private PMSConfig cachedConfig;

	@PostConstruct
	public void postConstruct() {
		// Load config from DB if exists, else use temporary in-memory default
		cachedConfig = pmsConfigRepository.findById(1L).orElseGet(() -> {
			PMSLogger.warn("PMSConfig not found. Using temporary in-memory default.");
			PMSConfig defaultConfig = new PMSConfig();
			defaultConfig.setId(1L);
			defaultConfig.setPharmacyName("Default Pharmacy");
			defaultConfig.setPharmacyEmail("email@example.com");
			defaultConfig.setPharmacyAddress("Default Address");
			defaultConfig.setCurrencySymbol("$");
			return defaultConfig;
		});

		PMSLogger.info("PMSConfig loaded");
		PMSLogger.setLogLevel(cachedConfig.getLogLevel());
	}

	public boolean doesConfigExists() {
		return pmsConfigRepository.existsById(1L);
	}

	// Cached method
	public PMSConfig getConfig() {
		if (cachedConfig == null) {
			cachedConfig = pmsConfigRepository.getConfig();
		}
		return cachedConfig;
	}

	public PMSConfig saveConfig(PMSConfig config) {
		cachedConfig = pmsConfigRepository.save(config);
		PMSLogger.setLogLevel(cachedConfig.getLogLevel());
		return cachedConfig;
	}

	public PMSConfig saveConfig() {
		return saveConfig(cachedConfig);
	}

	public void refreshCache() {
		pmsConfigRepository.findById(1L).ifPresent(config -> {
			cachedConfig = config;
			PMSLogger.debug("PMSConfig cache refreshed from DB");
		});
	}

}
