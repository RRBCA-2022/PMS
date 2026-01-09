package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import org.springframework.stereotype.Controller;
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

}
