package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}