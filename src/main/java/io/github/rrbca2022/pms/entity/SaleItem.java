package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sale_item")
public class SaleItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String productName;
	private int quantity;
	private double price;

	@ManyToOne
	@JoinColumn(name = "sale_id", nullable = false)
	private Sale sale;
}