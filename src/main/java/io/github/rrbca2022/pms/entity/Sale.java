package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime timestamp;

	private Double totalAmount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private User seller;

	@Column(name = "seller_name")
	private String sellerName;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleItem> items = new ArrayList<>();

}
