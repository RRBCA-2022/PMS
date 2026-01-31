package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.services.PMSConfigService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;

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
		model.addAttribute("pharmacyName", pmsConfigService.getConfig().getPharmacyName());
	}

	@ModelAttribute
	public void addPMSVersion(ModelMap model) {
		model.addAttribute("version", appVersion);
	}

	@ModelAttribute
	public void addCurrencySymbol(ModelMap model) {
		model.addAttribute("currencySymbol", pmsConfigService.getConfig().getCurrencySymbol());
	}

	@ModelAttribute("medicine")
	public Medicine medicine() {
		return new Medicine();
	}

	@ModelAttribute("category")
	public Category category() {
		return new Category();
	}

	@ModelAttribute
	public void addUser(HttpSession session, Model model) {
		User user = (User) session.getAttribute("LOGGED_USER");
		if (user != null) {
			model.addAttribute("sellerId", user.getId());
			model.addAttribute("sellerName", user.getName());
		}
	}

	@ModelAttribute
	public void addLocalDate(ModelMap model) {
		model.addAttribute("currentDate", LocalDate.now());
	}


}
