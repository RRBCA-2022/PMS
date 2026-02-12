package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sale, String> {
    List<Sale> findByTimestampAfter(LocalDateTime timestamp);

    @Query("SELECT SUM(s.quantity) FROM SaleItem s " +
            "WHERE s.medicine.id = :medicineId " +
            "AND s.sale.timestamp >= :startDate")
    Integer sumQuantityByMedicineAndDateAfter(
            @Param("medicineId") Long medicineId,
            @Param("startDate") LocalDateTime startDate
    );

    @Query("SELECT SUM(s.totalAmount) FROM Sale s")
    Double calculateTotalRevenue();


}
