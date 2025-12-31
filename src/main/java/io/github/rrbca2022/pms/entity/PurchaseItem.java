package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_item")
public class PurchaseItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int qty;

	private double price;

	@ManyToOne
	@JoinColumn(name = "purchase_id", nullable = false)
	private Purchase purchase;

	@ManyToOne
	@JoinColumn(name = "med_id", nullable = false)
	private Medicine medicine;

}
