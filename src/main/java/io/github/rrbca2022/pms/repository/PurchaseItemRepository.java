package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.PurchaseItem;
import io.github.rrbca2022.pms.entity.PurchaseOrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

	/**
	 * Reaches through PurchaseItem to the Purchase parent to find completed orders.
	 * This is the most efficient way to fetch historical lead time data.
	 */
	@Query("SELECT pi FROM PurchaseItem pi JOIN pi.purchase p " +
			"WHERE pi.medicine.id = :medicineId AND p.orderStatus = :status " +
			"ORDER BY p.reviewTimestamp DESC")
	List<PurchaseItem> findRecentApprovedPurchases(
			@Param("medicineId") String medicineId,
			@Param("status") PurchaseOrderStatus status,
			Pageable pageable);
}