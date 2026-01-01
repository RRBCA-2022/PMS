package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicine")
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	private String description;

	private int qty;

	@Enumerated(EnumType.STRING)
	@Column(name = "qty_unit", nullable = false)
	private MedicineUnitType qtyUnit;

	private double price;

	private String location; // e.g., "Shelf A3"

	private String manufacturer;

	private LocalDate mfgDate;  // Manufacturing date

	private LocalDate expDate;  // Expiry date

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

}
