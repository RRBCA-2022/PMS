package io.github.rrbca2022.pms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.rrbca2022.pms.utils.id.AlphaNumericIdGenerator;
import io.github.rrbca2022.pms.utils.id.IdPrefix;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier")
@IdPrefix("SUP-")
public class Supplier {

	@Id
	@GeneratedValue(generator = "alphanumeric-id")
	@GenericGenerator(name = "alphanumeric-id", type = AlphaNumericIdGenerator.class)
	private String id;

	private String name;
	private String address;

	private String phone;
	private String email;

	@OneToMany(mappedBy = "supplier")
	@JsonIgnore
	private List<Purchase> purchases = new ArrayList<>();

}