package io.github.rrbca2022.pms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedItemDTO {
	private String id;     // medicine ID
	private String name;
	private double price;
	private int qty;
}
