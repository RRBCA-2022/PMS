package io.github.rrbca2022.pms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PurchaseFormDTO {
	private Long supplierId;       // <- add this
	private String supplierName;
	private LocalDate orderDate;
	private List<MedItemDTO> items;
}
