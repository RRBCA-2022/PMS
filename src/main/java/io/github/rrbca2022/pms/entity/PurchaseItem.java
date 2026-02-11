package io.github.rrbca2022.pms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "purchase_item")
public class PurchaseItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int qty;

	private double price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_id", nullable = false)
	@JsonIgnore
	@JsonBackReference
	private Purchase purchase;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "med_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JsonIgnore
	private Medicine medicine;

	@Column(name = "med_name")
	private String medicineName;  // Store the medicine name at the time of purchase

}
