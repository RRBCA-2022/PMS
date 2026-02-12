package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DevController {

	private final InventoryService inventoryService;

	/**
	 * Manually triggers the Forecasting and ROP update logic.
	 * Access should be restricted to Admins only.
	 */
	@PostMapping("/trigger-forecast")
	public String triggerUpdate(RedirectAttributes redirectAttributes) {
		inventoryService.triggerManualUpdate();
		redirectAttributes.addFlashAttribute("successMessage", "Inventory forecasting updated successfully!");
		return "redirect:/profile";
	}
}