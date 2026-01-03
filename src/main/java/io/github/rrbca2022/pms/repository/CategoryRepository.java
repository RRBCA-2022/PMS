package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


	/* =========================
	   BASIC LOOKUPS
	   ========================= */

	// Category name may or may not be unique depending on business rules
	Optional<Category> findByName(String name);

	// Case-insensitive lookup
	Optional<Category> findByNameIgnoreCase(String name);

	// Partial name search (useful for admin panels)
	List<Category> findByNameContainingIgnoreCase(String keyword);


	/* =========================
	   EXISTENCE / VALIDATION
	   ========================= */

	// Prevent duplicate category names
	boolean existsByName(String name);


	/* =========================
	   RELATIONSHIP-BASED QUERIES
	   ========================= */

	// Categories that contain at least one medicine
	List<Category> findByMedicinesIsNotEmpty();

	// Categories with no medicines
	List<Category> findByMedicinesIsEmpty();

}

