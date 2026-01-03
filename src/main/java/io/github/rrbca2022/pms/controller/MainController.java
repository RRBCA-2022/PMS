package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.PMSConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	private final PMSConfigService pmsConfigService;

	public MainController(PMSConfigService pmsConfigService) {
		this.pmsConfigService = pmsConfigService;
	}

	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("pharmacyName", pmsConfigService.getPharmacyName());
		return "dashboard";
	}

	@RequestMapping("/medicine")
	public String medicine () {
		return "medicine";
	}

	@RequestMapping("/add_medicine")
	public String add_medicine () {
		return "add_medicine";
	}

	@RequestMapping("/category")
	public String category () {
		return "category";
	}

	@RequestMapping("/add_category")
	public String add_category () {return "add_category";}

	@RequestMapping("/purchase")
	public String purchase () {
		return "purchase";
	}

	@RequestMapping("/sales")
	public String sales () {
		return "sales";
	}

	@RequestMapping("/user")
	public String user () {
		return "user";
	}

	@RequestMapping("/supplier")
	public String supplier(){
		return "supplier";
	}

}
