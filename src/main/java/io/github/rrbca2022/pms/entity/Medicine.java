package io.github.rrbca2022.pms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.rrbca2022.pms.utils.id.AlphaNumericIdGenerator;
import io.github.rrbca2022.pms.utils.id.IdPrefix;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicine")
@IdPrefix("MED-")
public class Medicine {

	@Id
	@GeneratedValue(generator = "alphanumeric-id")
	@GenericGenerator(name = "alphanumeric-id", type = AlphaNumericIdGenerator.class)
	private String id;

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

	@OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MedicineBatch> batches;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Category category;


	@Transient
	public LocalDate getSoonestExpiryDate() {
		// Check if batches is null or empty before streaming
		if (this.batches == null || this.batches.isEmpty()) {
			return null;
		}
		return this.batches.stream()
				.filter(b -> b.getStockQuantity() != null && b.getStockQuantity() > 0)
				.map(MedicineBatch::getExpiryDate)
				.filter(java.util.Objects::nonNull)
				.min(java.util.Comparator.naturalOrder())
				.orElse(null);
	}

	@Transient
	public String getInventoryStatus() {
		if (this.qty <= 0) return "OUT_OF_STOCK";

		// Re-use the safe method above
		LocalDate soonestDate = getSoonestExpiryDate();

		if (soonestDate != null) {
			long daysToExpiry = java.time.temporal.ChronoUnit.DAYS.between(
					java.time.LocalDate.now(), soonestDate);

			if (daysToExpiry <= 0) return "EXPIRED";
			if (daysToExpiry <= 30) return "EXPIRY_SOON";
		}

		if (this.qty < 10) return "LOW_STOCK";

		return "NORMAL";
	}
}
