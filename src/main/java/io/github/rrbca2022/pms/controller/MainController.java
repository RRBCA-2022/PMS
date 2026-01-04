package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.PMSConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

	private final PMSConfigService pmsConfigService;

	public MainController(PMSConfigService pmsConfigService) {
		this.pmsConfigService = pmsConfigService;
	}

	@ModelAttribute
	public void addPharmacyName(Model model) {
		model.addAttribute("pharmacyName", pmsConfigService.getPharmacyName());
	}

	@GetMapping("/dashboard")
	public String dashboard() { return "dashboard"; }

	@GetMapping("/purchase")
	public String purchase () {
		return "purchase";
	}

	@GetMapping("/sales")
	public String sales () {
		return "sales";
	}

	@GetMapping("/user")
	public String user () {
		return "user";
	}

	@GetMapping("/supplier")
	public String supplier(){
		return "supplier";
	}

}
