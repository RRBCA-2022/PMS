package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.DemandHistory;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.PurchaseItem;
import io.github.rrbca2022.pms.entity.PurchaseOrderStatus;
import io.github.rrbca2022.pms.repository.DemandHistoryRepository;
import io.github.rrbca2022.pms.repository.InventoryRepository;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.PurchaseItemRepository;
import io.github.rrbca2022.pms.utils.PMSLogger;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final MedicineRepository medicineRepository;
	private final PurchaseItemRepository purchaseItemRepository;
	private final InventoryRepository inventoryRepo; // Contains the getMonthlySales query
	private final DemandHistoryRepository historyRepository;

	public void triggerManualUpdate() {
		PMSLogger.info("Manual medicine stats update triggered");
		List<Medicine> medicines = medicineRepository.findAll();

		for (Medicine med : medicines) {
			try {
				refreshMedicineStats(med.getId());
			} catch (Exception e) {
				PMSLogger.error("Error processing medicine (" +  med.getId() + "): " + e.getMessage());
			}
		}
	}

	public int calculateActualLeadTime(String medicineId) {
		// Fetch last 3 Approved purchases
		List<PurchaseItem> items = purchaseItemRepository.findRecentApprovedPurchases(
				medicineId, PurchaseOrderStatus.APPROVED, PageRequest.of(0, 3));

		if (items.isEmpty()) return 7; // Default fallback

		double averageDays = items.stream()
				.filter(item -> item.getPurchase().getReviewTimestamp() != null)
				.mapToLong(item -> Duration.between(
						item.getPurchase().getTimestamp(),
						item.getPurchase().getReviewTimestamp()).toDays())
				.average()
				.orElse(7.0);

		// Professional practice: Use Math.ceil to ensure we don't under-order
		return (int) Math.ceil(averageDays);
	}

	/**
	 * Updates both the Forecast and the Lead Time for a specific medicine.
	 * This ensures the ROP adapts to both sales trends and supplier speed.
	 */
	@Transactional
	public void refreshMedicineStats(String medicineId) {
		Medicine med = medicineRepository.findById(medicineId)
				.orElseThrow(() -> new RuntimeException("Medicine not found"));

		// 1. Update Lead Time from history
		med.setLeadTime(calculateActualLeadTime(med.getId()));

		// 2. Calculate Demand Forecast (Ft = αDt + (1-α)Ft-1)
		Integer actualSales = inventoryRepo.getMonthlySales(med.getId(), LocalDateTime.now().minusMonths(1));
		actualSales = (actualSales != null) ? actualSales : 0;

		double newForecast = (med.getAlpha() * actualSales) + ((1 - med.getAlpha()) * med.getLastForecast());
		med.setLastForecast(newForecast);

		// 3. Save to Audit History
		DemandHistory history = DemandHistory.builder()
				.medicine(med)
				.periodDate(LocalDate.now())
				.actualSales(actualSales)
				.forecastedValue(newForecast)
				.alphaUsed(med.getAlpha())
				.build();

		historyRepository.save(history);
		medicineRepository.save(med);
	}
}

