package io.github.rrbca2022.pms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemDTO {
	private Long id;     // medicine ID
	private int qty;     // quantity sold
}