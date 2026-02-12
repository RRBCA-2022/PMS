package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.dto.ReportDTO;
import io.github.rrbca2022.pms.entity.MedicineBatch;
import io.github.rrbca2022.pms.entity.Sale;
import io.github.rrbca2022.pms.entity.SaleItem;
import io.github.rrbca2022.pms.repository.MedicineBatchRepository;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final MedicineBatchRepository batchRepository;
    private final MedicineRepository medicineRepository;
    private final SalesRepository salesRepository;

    public ReportDTO generateDailyReport() {
        ReportDTO report = new ReportDTO();
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();

        // 1. Fetch Sales and Extract Items
        List<Sale> todaysSales = salesRepository.findByTimestampAfter(startOfDay);

        List<SaleItem> allItemsSoldToday = todaysSales.stream()
                .flatMap(sale -> sale.getItems().stream())
                .collect(Collectors.toList());

        report.setDailySales(allItemsSoldToday);

        double revenue = allItemsSoldToday.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        report.setTotalDailyRevenue(revenue);


        // 2. Out of Stock (PRIORITY 1)
        // Ensure we only show items that are truly 0 across all batches
        report.setOutOfStockItems(medicineRepository.findByQtyLessThanEqual(0));

        // 3. Already Expired (PRIORITY 2)
        // Fetch only batches that have stock > 0 but are past expiry
        report.setExpiredItems(batchRepository.findByExpiryDateBeforeAndStockQuantityGreaterThan(today.plusDays(1), 0));


        // 4. Expiring Soon (PRIORITY 3)
        // Only include batches that have stock AND are within the 30-day window
        // This prevents 0-stock items from cluttering this alert list
       // report.setExpiringSoonItems(batchRepository.findByExpiryDateBetweenAndStockQuantityGreaterThan(
         //       today, today.plusDays(30), 0));

        List<MedicineBatch> soon = batchRepository.findByExpiryDateBetweenAndStockQuantityGreaterThan(
                today.plusDays(1), today.plusDays(30), 0);
        report.setExpiringSoonItems(soon);
        return report;
    }

    public Map<String, Object> getTrendData(String period) {
        LocalDateTime startPoint;

        // 1. Determine the start date based on the chosen period
        switch (period.toLowerCase()) {
            case "daily":
                startPoint = LocalDate.now().minusDays(1).atStartOfDay(); // Last 24-48 hours
                break;
            case "monthly":
                startPoint = LocalDate.now().minusMonths(1).atStartOfDay(); // Last 30 days
                break;
            case "weekly":
            default:
                startPoint = LocalDate.now().minusDays(6).atStartOfDay(); // Default last 7 days
                break;
        }

        List<Sale> sales = salesRepository.findByTimestampAfter(startPoint);

        // 2. Grouping logic
        Map<LocalDate, Double> totals = sales.stream()
                .collect(Collectors.groupingBy(
                        sale -> sale.getTimestamp().toLocalDate(),
                        Collectors.summingDouble(sale -> sale.getItems().stream()
                                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum())
                ));

        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        // 3. Generate the range of dates to ensure we show 0 for days with no sales
        int daysToLookBack = period.equalsIgnoreCase("monthly") ? 30 : (period.equalsIgnoreCase("daily") ? 1 : 6);

        for (int i = daysToLookBack; i >= 0; i--) {
            LocalDate d = LocalDate.now().minusDays(i);
            labels.add(d.toString());
            values.add(totals.getOrDefault(d, 0.0));
        }

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", labels);
        chartData.put("values", values);
        return chartData;
    }
}
