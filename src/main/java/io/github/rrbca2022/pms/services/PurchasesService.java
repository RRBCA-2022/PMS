package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.dto.MedItemDTO;
import io.github.rrbca2022.pms.dto.PurchaseFormDTO;
import io.github.rrbca2022.pms.entity.*;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.PurchasesRepository;
import io.github.rrbca2022.pms.utils.PMSLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor()
public class PurchasesService {
    private final MedicineRepository medicineRepository;
    private final PurchasesRepository purchasesRepository;
    private final SuppliersService suppliersService;

    public List<Purchase> getAllPurchases() {
        return purchasesRepository.findAll();
    }

    public Purchase getPurchaseById(String id) {
        return purchasesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found with ID: " + id));
    }

    public double newPurchase(PurchaseFormDTO purchaseDTO) {

        //create new purchase
        Purchase purchase= new Purchase();
        purchase.setTimestamp(purchaseDTO.getOrderDate().atTime(LocalTime.now()));
        purchase.setOrderStatus(PurchaseOrderStatus.PENDING);

        Supplier supplier;
        if (purchaseDTO.getSupplierId() != null && !purchaseDTO.getSupplierId().isEmpty()) {
            // user selected from dropdown
            supplier = suppliersService.getSupplierById(purchaseDTO.getSupplierId());

            PMSLogger.debug("Supplier ID Found");
            PMSLogger.debug(purchaseDTO.getSupplierId());
        } else {
            // user typed manually; try to create new
            supplier = new Supplier();
            supplier.setName(purchaseDTO.getSupplierName());
            suppliersService.saveSupplier(supplier); // create new supplier

            PMSLogger.debug("Supplier ID Not Found");
            PMSLogger.debug(purchaseDTO.getSupplierId());
        }
        purchase.setSupplierName(supplier.getName());
        purchase.setSupplier(supplier);

        double totalAmount = 0.0;
        for(MedItemDTO item: purchaseDTO.getItems()) {

            Medicine medicine = medicineRepository.findById(item.getId()).orElseThrow(()->new RuntimeException("Medicine not found"));

            PurchaseItem items=new PurchaseItem();
            items.setMedicineName(medicine.getName());
            items.setMedicine(medicine);
            items.setQty(item.getQty());
            items.setPrice(item.getPrice());

            double itemsTotal=items.getQty()* items.getPrice();
            items.setPurchase(purchase);
            purchase.getItems().add(items);
            totalAmount+=itemsTotal;

        }
        purchase.setTotalAmount(totalAmount);

        purchasesRepository.save(purchase);

        return totalAmount;
    }

    @Transactional
    public void approvePurchase(String id, User user) {
        Purchase po = purchasesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));

        // Fix for Enum comparison
        if (!"PENDING".equals(po.getOrderStatus().name())) {
            throw new IllegalStateException("Only pending orders can be approved");
        }

        po.setOrderStatus(PurchaseOrderStatus.APPROVED);
        po.setReviewTimestamp(LocalDateTime.now());
        po.setReviewer(user);
        po.setReviewerName(user.getName());

        for(PurchaseItem item: po.getItems()) {
            Medicine med = item.getMedicine();

            if (med == null) {
                throw new RuntimeException("Medicine not found for item: " + item.getId());
            }
            //increment stock
            med.setQty(med.getQty() + item.getQty());
            medicineRepository.save(med);
        }
        purchasesRepository.save(po);
    }

    @Transactional
    public void rejectPurchase(String id, User user){
        Purchase po = purchasesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));

        //only status update med not
        po.setOrderStatus(PurchaseOrderStatus.REJECTED);
        po.setReviewTimestamp(LocalDateTime.now());
        po.setReviewer(user);
        po.setReviewerName(user.getName());

        purchasesRepository.save(po);

    }

    /**
     * Calculates the total value of approved purchase (Qty * Price).
     */
    public double getTotalInventoryValue() {
        Double inventoryValue = purchasesRepository.calculateTotalApprovedCost();
        return (inventoryValue != null) ? inventoryValue : 0.0;
    }

}
