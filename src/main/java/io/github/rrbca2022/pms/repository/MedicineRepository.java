package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.MedicineUnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, String> {


    List<Medicine> findByNotificationIsNotNull();

    // Counts how many medicines are low (for the red bubble on the bell icon)
    long countByNotificationIsNotNull();
    // Find items where total quantity is 0
    List<Medicine> findByQtyLessThanEqual(int threshold);
	/* =========================
	   BASIC LOOKUPS
	   ========================= */

    // Multiple medicines may share a name
    Optional<Medicine> findByName(String name);

    // Case-insensitive name search
    Optional<Medicine> findByNameIgnoreCase(String name);

    // Partial name search (e.g. "para")
    List<Medicine> findByNameContainingIgnoreCase(String keyword);

    // Find all medicines by unit type
    List<Medicine> findByQtyUnit(MedicineUnitType qtyUnit);

	/* =========================
        SORTING QUERIES
	   ========================= */

    // Medicines sorted by name (A → Z)
    List<Medicine> findAllByOrderByNameAsc();

    // Medicines sorted by name (Z → A)
    List<Medicine> findAllByOrderByNameDesc();

    // Medicines sorted by expiry date (soonest first)
    List<Medicine> findAllByOrderByExpDateAsc();

    // Medicines sorted by expiry date (latest first)
    List<Medicine> findAllByOrderByExpDateDesc();

    // Medicines sorted by price (low → high)
    List<Medicine> findAllByOrderByPriceAsc();

    // Medicines sorted by price (high → low)
    List<Medicine> findAllByOrderByPriceDesc();


	/* =========================
	   CATEGORY-BASED QUERIES
	   ========================= */

    // All medicines in a category (by id)
    List<Medicine> findByCategoryId(String categoryId);

    // All medicines in a category (by name)
    List<Medicine> findByCategoryName(String categoryName);

    // Medicines by category AND name
    List<Medicine> findByCategoryNameAndNameIgnoreCase(
            String categoryName,
            String name
    );


	/* =========================
	   STOCK / INVENTORY
	   ========================= */

    // Medicines with low stock
    List<Medicine> findByQtyLessThan(int qty);

    // Medicines that are out of stock
    List<Medicine> findByQty(int qty);

    // Medicines stored at a specific location
    List<Medicine> findByLocationContainingIgnoreCase(String keyword);


	/* =========================
	   EXPIRY & DATE QUERIES
	   ========================= */

    // Already expired medicines
    List<Medicine> findByExpDateBeforeOrderByExpDateAsc(LocalDate date);

    // Medicines expiring within a range
    List<Medicine> findByExpDateBetween(
            LocalDate start,
            LocalDate end
    );

    // Manufactured after a certain date
    List<Medicine> findByMfgDateAfter(LocalDate date);


	/* =========================
	   PRICE-BASED QUERIES
	   ========================= */

    // Medicines cheaper than a price
    List<Medicine> findByPriceLessThan(double price);

    // Medicines within a price range
    List<Medicine> findByPriceBetween(
            double min,
            double max
    );


	/* =========================
	   EXISTENCE / VALIDATION
	   ========================= */

    // Useful for validation before insert
    boolean existsByName(String name);



}
