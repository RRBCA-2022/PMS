package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.repository.PMSConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PMSConfigService {

	private final PMSConfigRepository pmsConfigRepository;

	public PMSConfigService(PMSConfigRepository pmsConfigRepository) {
		this.pmsConfigRepository = pmsConfigRepository;
	}

	public String getPharmacyName() {
		return pmsConfigRepository.getConfig().getPharmacyName();
	}

	public void setPharmacyName(String pharmacyName) {
		pmsConfigRepository.getConfig().setPharmacyName(pharmacyName);
	}

	public String getPharmacyEmail() {
		return pmsConfigRepository.getConfig().getPharmacyEmail();
	}

	public void setPharmacyEmail(String pharmacyEmail) {
		pmsConfigRepository.getConfig().setPharmacyEmail(pharmacyEmail);
	}

	public String getPharmacyAddress() {
		return pmsConfigRepository.getConfig().getPharmacyAddress();
	}

	public void setPharmacyAddress(String pharmacyAddress) {
		pmsConfigRepository.getConfig().setPharmacyAddress(pharmacyAddress);
	}


}
