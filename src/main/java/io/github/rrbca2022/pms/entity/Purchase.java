package io.github.rrbca2022.pms.entity;

import io.github.rrbca2022.pms.utils.id.AlphaNumericIdGenerator;
import io.github.rrbca2022.pms.utils.id.IdPrefix;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase")
@IdPrefix("PUR-")
public class Purchase {

	@Id
	@GeneratedValue(generator = "alphanumeric-id")
	@GenericGenerator(name = "alphanumeric-id", type = AlphaNumericIdGenerator.class)
	private String id;

	@Column(name = "total_amount")
	private Double totalAmount;

	private LocalDateTime timestamp;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private PurchaseOrderStatus orderStatus = PurchaseOrderStatus.PENDING;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reviewer_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private User reviewer;

	@Column(name = "reviewer_name", nullable = true)
	private String reviewerName;

	@Column(name = "review_timestamp", nullable = true)
	private LocalDateTime reviewTimestamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Supplier supplier;

	@Column(name = "supplier_name")
	private String supplierName;

	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseItem> items = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		if (orderStatus == null) {
			orderStatus = PurchaseOrderStatus.PENDING;
		}
	}

	public int calculateTotalQuantity() {
		return items.stream()
				.mapToInt(PurchaseItem::getQty)
				.sum();
	}

}