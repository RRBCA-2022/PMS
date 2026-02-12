package io.github.rrbca2022.pms.scheduler;

import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryScheduler {

	private final MedicineRepository medicineRepository;
	private final InventoryService inventoryService;

	/**
	 * Orchestrates the monthly forecasting and ROP updates for all medicines.
	 * Runs at 00:00:00 on the 1st day of every month.
	 */
	@Scheduled(cron = "0 0 0 1 * ?")
	public void performMonthlyInventoryCheck() {
		log.info("Starting monthly demand forecasting and ROP update.");

		medicineRepository.findAll().forEach(med -> {
			try {
				inventoryService.refreshMedicineStats(med.getId());
			} catch (Exception e) {
				log.error("Failed to update intelligence for medicine ID: {}", med.getId(), e);
			}
		});

		log.info("Elysium Intelligence: Monthly update completed successfully.");
	}
}