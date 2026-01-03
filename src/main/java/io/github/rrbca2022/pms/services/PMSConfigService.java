package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.PMSConfig;
import io.github.rrbca2022.pms.repository.PMSConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PMSConfigService {

	private final PMSConfigRepository pmsConfigRepository;
	private PMSConfig cachedConfig;

	public PMSConfigService(PMSConfigRepository pmsConfigRepository) {
		this.pmsConfigRepository = pmsConfigRepository;
	}

	// Cached method
	public PMSConfig getConfig() {
		if (cachedConfig == null) {
			cachedConfig = pmsConfigRepository.getConfig();
		}
		return cachedConfig;
	}

	public PMSConfig saveConfig(PMSConfig config) {
		cachedConfig = pmsConfigRepository.save(config); // update cache manually
		return cachedConfig;
	}

	public String getPharmacyName() {
		System.out.println(getConfig().getPharmacyName());
		return getConfig().getPharmacyName();
	}

	public void setPharmacyName(String pharmacyName) {
		getConfig().setPharmacyName(pharmacyName);
	}

	public String getPharmacyEmail() {
		return getConfig().getPharmacyEmail();
	}

	public void setPharmacyEmail(String pharmacyEmail) {
		getConfig().setPharmacyEmail(pharmacyEmail);
	}

	public String getPharmacyAddress() {
		return getConfig().getPharmacyAddress();
	}

	public void setPharmacyAddress(String pharmacyAddress) {
		getConfig().setPharmacyAddress(pharmacyAddress);
	}


}
