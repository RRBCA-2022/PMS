package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "config")
public class PMSConfig {

	@Id
	private Long id = 1L;

	@Column(nullable = false)
	private String pharmacyName;

	@Column(nullable = false)
	private String pharmacyEmail;

	@Column(nullable = false)
	private String pharmacyAddress;

	@Column(nullable = false)
	private String currencySymbol;

	@Enumerated(EnumType.STRING)
	@Column(name = "log_level", nullable = false)
	private LogLevel logLevel = LogLevel.INFO;
}