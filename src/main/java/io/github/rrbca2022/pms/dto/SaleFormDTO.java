package io.github.rrbca2022.pms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SaleFormDTO {
	private LocalDate timestamp;
	private Long sellerId;
	private String sellerName;
	private List<MedItemDTO> items;
}
