package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.DemandHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DemandHistoryRepository extends JpaRepository<DemandHistory, Long> {

	/**
	 * Retrieves the history for a specific medicine, ordered by date.
	 * Perfect for rendering 'Actual vs Forecast' line charts.
	 */
	List<DemandHistory> findByMedicineIdOrderByPeriodDateAsc(String medicineId);

	/**
	 * Finds the most recent history record.
	 */
	DemandHistory findTopByMedicineIdOrderByPeriodDateDesc(String medicineId);
}