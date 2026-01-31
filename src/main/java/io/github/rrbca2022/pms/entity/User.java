package io.github.rrbca2022.pms.entity;

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
@Table(name = "`user`")
@IdPrefix("USR-")
public class User {

	@Id
	@GeneratedValue(generator = "alphanumeric-id")
	@GenericGenerator(name = "alphanumeric-id", type = AlphaNumericIdGenerator.class)
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private AccountType accountType;

	@OneToMany(mappedBy = "seller")
	private List<Sale> sales = new ArrayList<>();

}
