package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.DemandHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<DemandHistory, Long> {

	/**
	 * Aggregates total sales for a specific medicine within the last 30 days.
	 * This represents 'Dt' in your Exponential Smoothing algorithm.
	 */
	@Query("SELECT SUM(si.quantity) FROM SaleItem si " +
			"WHERE si.medicine.id = :id " +
			"AND si.sale.timestamp >= :since")
	Integer getMonthlySales(@Param("id") String id, @Param("since") LocalDateTime since);

	/**
	 * Fetches the timestamps of recent completed purchases to calculate Lead Time.
	 * We use Pageable to limit results (e.g., top 3) to get a moving average.
	 */
	@Query("SELECT p.timestamp, p.reviewTimestamp FROM Purchase p " +
			"JOIN p.items pi " +
			"WHERE pi.medicine.id = :id " +
			"AND p.orderStatus = 'COMPLETED' " +
			"ORDER BY p.reviewTimestamp DESC")
	List<Object[]> getRecentLeadTimes(@Param("id") String id, Pageable pageable);
}