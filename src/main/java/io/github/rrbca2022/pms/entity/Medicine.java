package io.github.rrbca2022.pms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate mfgDate;  // Manufacturing date

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate expDate;  // Expiry date

	@AssertTrue(message = "Expiry date must be after manufacturing date")
	public boolean isValidDates() {
		if (mfgDate == null || expDate == null) return true;
		return expDate.isAfter(mfgDate);
	}

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

}
