package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale_item")
public class SaleItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int quantity;

	private double price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id", nullable = false)
	private Sale sale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "med_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Medicine medicine;

	@Column(name = "med_name")
	private String medicineName;

}