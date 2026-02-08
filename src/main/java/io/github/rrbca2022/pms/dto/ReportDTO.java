package io.github.rrbca2022.pms.dto;

import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.MedicineBatch;
import io.github.rrbca2022.pms.entity.SaleItem;
import lombok.Data;

import java.util.List;
@Data
public class ReportDTO {
    private List<SaleItem> dailySales;
    private List<MedicineBatch> expiredItems;
    private List<Medicine> outOfStockItems;
    private List<MedicineBatch> expiringSoonItems;

    private double totalDailyRevenue=0.0;
}
