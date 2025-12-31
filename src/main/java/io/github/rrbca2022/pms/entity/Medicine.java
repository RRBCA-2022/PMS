package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "medicine")
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private int qty;

	private double price;

	private String location; // e.g., "Shelf A3"

	private LocalDate mfgDate;  // Manufacturing date

	private LocalDate expDate;  // Expiry date

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	// getters and setters
}
