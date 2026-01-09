package io.github.rrbca2022.pms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SaleFormDTO {
	private LocalDate timestamp;   // sale date
	private List<Long> id;         // medicine IDs
	private List<Integer> qty;     // quantities
}
