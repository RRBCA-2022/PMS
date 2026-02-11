package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

	private final MedicineService medicineService;
	private final CategoryService categoryService;
	private final MedicineRepository medicineRepository;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		List<Medicine> lowStockAlerts = medicineRepository.findByNotificationIsNotNull();

		// 2. Get the count for the notification badge
		long alertCount = medicineRepository.countByNotificationIsNotNull();

		model.addAttribute("alerts", lowStockAlerts);
		model.addAttribute("alertCount", alertCount);
		return "dashboard";
	}

	@GetMapping("/profile")
	public String profile(Model model, HttpSession session) {
		User user = (User) session.getAttribute("LOGGED_USER");
		model.addAttribute("user", user);
		return "profile";
	}



}
