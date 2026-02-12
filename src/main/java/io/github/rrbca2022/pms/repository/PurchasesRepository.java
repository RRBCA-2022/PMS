package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasesRepository extends JpaRepository<Purchase, String> {

    @Query("SELECT AVG(DATEDIFF(p.reviewTimestamp, p.timestamp)) " +
            "FROM Purchase p JOIN p.items i " +
            "WHERE i.medicine.id = :medId AND p.orderStatus = 'APPROVED'")
    Double getAverageLeadTimeForMedicine(@Param("medId") String medId);

    @Query("SELECT SUM(p.totalAmount) FROM Purchase p WHERE p.orderStatus = 'APPROVED'")
    Double calculateTotalApprovedCost();

}
