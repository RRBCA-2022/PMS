package io.github.rrbca2022.pms.entity;

import io.github.rrbca2022.pms.utils.id.AlphaNumericIdGenerator;
import io.github.rrbca2022.pms.utils.id.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"items", "seller"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdPrefix("SALE-")
public class Sale {

	@Id
	@GeneratedValue(generator = "alphanumeric-id")
	@GenericGenerator(name = "alphanumeric-id", type = AlphaNumericIdGenerator.class)
	private String id;

	private LocalDateTime timestamp;

	@Column(name = "total_amount")
	private Double totalAmount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private User seller;

	@Column(name = "seller_name")
	private String sellerName;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleItem> items = new ArrayList<>();

	public int calculateTotalQuantity() {
		return items.stream()
				.mapToInt(SaleItem::getQuantity)
				.sum();
	}

}
