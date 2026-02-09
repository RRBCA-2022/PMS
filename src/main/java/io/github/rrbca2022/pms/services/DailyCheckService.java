package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.PurchasesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyCheckService {

    private final MedicineRepository medicineRepository;
    private final SalesService salesService;
    private final PurchasesRepository purchaseOrderRepository;

    @Scheduled(cron = "0 0 8 * * *") // Runs every morning at 8:00 AM
    public void dailyStockCheck() {
        List<Medicine> medicines = medicineRepository.findAll();

        for (Medicine med : medicines) {
            int currentROP = calculateROP(med.getId());

            if (med.getQty() <= currentROP) {
                // Set the message directly in the med table column
                med.setNotification("Low Stock: " + med.getQty() + " remaining (ROP: " + currentROP + ")");
            } else {
                // Clear the column if stock is refilled
                med.setNotification(null);
            }
            // Save the update back to the med table
            medicineRepository.save(med);
        }
    }

    public int calculateROP(String medicineId) {
        Medicine med = medicineRepository.findById(medicineId).orElseThrow();
        double ads = salesService.calculateAverageDailySales(medicineId);
        Double avgLT = purchaseOrderRepository.getAverageLeadTimeForMedicine(medicineId);

        int leadTime = (avgLT == null || avgLT <= 0) ? 7 : (int) Math.ceil(avgLT);
        int safetyStock = 10;

        double demandDuringLeadTime = ads * leadTime;
        return Math.max((int) Math.ceil(demandDuringLeadTime + safetyStock), safetyStock);
    }

}
