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
@Table(name = "category")
@IdPrefix("CTG-")
public class Category {

	@Id
	@GeneratedValue(generator = "alphanumeric-id")
	@GenericGenerator(name = "alphanumeric-id", type = AlphaNumericIdGenerator.class)
	private String id;

	@Column(unique = true, nullable = false)
	private String name;

	private String description;

	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Medicine> medicines = new ArrayList<>();

}
