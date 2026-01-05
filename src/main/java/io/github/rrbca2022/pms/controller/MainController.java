package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.PMSConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	private final PMSConfigService pmsConfigService;
	private final CategoryService categoryService;

	public MainController(PMSConfigService pmsConfigService, CategoryService categoryService) {
		this.pmsConfigService = pmsConfigService;
		this.categoryService = categoryService;
	}

	@GetMapping("/dashboard")
	public String dashboard() { return "dashboard"; }

	@GetMapping("/purchase")
	public String purchase (ModelMap model) {
		model.addAttribute("medicine", new Medicine());
		model.addAttribute("mode","Add");
		model.addAttribute("categories", categoryService.getAllCategories());
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

}
