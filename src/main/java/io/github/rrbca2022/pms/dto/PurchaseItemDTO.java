package io.github.rrbca2022.pms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseItemDTO {
	private Long id;     // medicine ID
	private String name;
	private double price;
	private int qty;
}
