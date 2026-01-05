package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.PMSConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

	@Value("${app.version}")
	private String appVersion;

	private final PMSConfigService pmsConfigService;

	public GlobalModelAttributes(PMSConfigService pmsConfigService) {
		this.pmsConfigService = pmsConfigService;
	}

	@ModelAttribute
	public void addPharmacyName(ModelMap model) {
		model.addAttribute("pharmacyName", pmsConfigService.getPharmacyName());
	}

	@ModelAttribute
	public void addPMSVersion(ModelMap model) {
		model.addAttribute("version", appVersion);
	}

	@ModelAttribute
	public void addCurrencySymbol(ModelMap model) {
		model.addAttribute("currencySymbol", pmsConfigService.getCurrencySymbol());
	}
}
