package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	private final MedicineService medicineService;
	private final CategoryService categoryService;

	public MainController(MedicineService medicineService, CategoryService categoryService) {
		this.medicineService = medicineService;
		this.categoryService = categoryService;
	}

	@GetMapping("/dashboard")
	public String dashboard() { return "dashboard"; }

	@GetMapping("/purchase")
	public String purchase (ModelMap model) {
		model.addAttribute("mode","Add");
		model.addAttribute("medicines", medicineService.getAllMedicines());
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
