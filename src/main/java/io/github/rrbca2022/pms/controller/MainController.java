package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.PMSConfig;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.repository.CategoryRepository;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.services.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

	private final CategoryRepository categoryRepository;
	private final MedicineRepository medicineRepository;
	private final PMSConfigService pmsConfigService;
	private final SalesService salesService;
	private final PurchasesService purchasesService;


	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		double totalRevenue = salesService.getTotalRevenue();
		double totalCost = purchasesService.getTotalInventoryValue(); // sum(qty * price)

		long totalMeds = medicineRepository.count();
		long totalCategories = categoryRepository.count();

		model.addAttribute("totalRevenue", totalRevenue);
		model.addAttribute("totalCost", totalCost);
		model.addAttribute("totalMeds", totalMeds);
		model.addAttribute("totalCategories", totalCategories);

		List<Medicine> reorderAdvice = medicineRepository.findAll().stream()
				.filter(m -> m.getQty() <= m.getCalculatedROP())
				.sorted(Comparator.comparingInt(Medicine::getQty))
				.limit(8)
				.toList();

		model.addAttribute("reorderAdvice", reorderAdvice);

		return "dashboard";
	}

	@GetMapping("/profile")
	public String profile(Model model, HttpSession session) {
		PMSConfig config = pmsConfigService.getConfig();
		User user = (User) session.getAttribute("LOGGED_USER");
		model.addAttribute("user", user);
		model.addAttribute("config", pmsConfigService.getConfig());
		return "profile";
	}



}
