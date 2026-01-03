package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.PMSConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PMSConfigRepository extends JpaRepository<PMSConfig, Long> {
	default PMSConfig getConfig() {
		return findById(1L).orElseThrow(
				() -> new RuntimeException("Pharmacy configuration not initialized")
		);
	}

}
