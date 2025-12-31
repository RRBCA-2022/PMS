package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supplier")
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String address;
	private String phone;
	private String email;

	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
	private List<Purchase> purchases = new ArrayList<>();

	// getters and setters
}