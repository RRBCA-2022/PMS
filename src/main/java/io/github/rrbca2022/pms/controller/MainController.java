package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

	private final MedicineService medicineService;
	private final CategoryService categoryService;

	@GetMapping("/dashboard")
	public String dashboard() { return "dashboard"; }

}
