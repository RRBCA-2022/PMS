package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.MedicineBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicineBatchRepository extends JpaRepository<MedicineBatch, Long> {

    @Query("SELECT b FROM MedicineBatch b WHERE b.medicine.id = :medId " +
            "AND b.stockQuantity > 0 ORDER BY b.expiryDate ASC")
    List<MedicineBatch> findAvailableBatchesFEFO(@Param("medId") String medId);

    // Find batches by expiry date ranges
    List<MedicineBatch> findByExpiryDateBeforeAndStockQuantityGreaterThan(LocalDate date, int qty);
    List<MedicineBatch> findByExpiryDateBetweenAndStockQuantityGreaterThan(LocalDate start, LocalDate end, int qty);
}
