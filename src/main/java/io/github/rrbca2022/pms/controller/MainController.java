package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.PMSConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	private final PMSConfigService pmsConfigService;

	public MainController(PMSConfigService pmsConfigService) {
		this.pmsConfigService = pmsConfigService;
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
