package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.dto.MedItemDTO;
import io.github.rrbca2022.pms.dto.PurchaseFormDTO;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.Purchase;
import io.github.rrbca2022.pms.entity.PurchaseItem;
import io.github.rrbca2022.pms.entity.Supplier;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.PurchasesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchasesService {
    private final MedicineRepository medicineRepository;
    private final PurchasesRepository purchasesRepository;
    private final SuppliersService suppliersService;

    public List<Purchase> getAllPurchases() {
        return purchasesRepository.findAll();
    }

    public double newPurchase(PurchaseFormDTO purchaseDTO) {

        //create new purchase
        Purchase purchase= new Purchase();
        purchase.setDatetime(purchaseDTO.getOrderDate().atTime(LocalTime.now()));

        Supplier supplier;

        if (purchaseDTO.getSupplierId() != null) {
            // user selected from dropdown
            supplier = suppliersService.getSupplierById(purchaseDTO.getSupplierId());
        } else {
            // user typed manually; try to create new
            supplier = new Supplier();
            supplier.setName(purchaseDTO.getSupplierName());
            suppliersService.saveSupplier(supplier); // create new supplier
        }
        purchase.setSupplierName(supplier.getName());
        purchase.setSupplier(supplier);

        double totalAmount = 0.0;
        for(MedItemDTO item: purchaseDTO.getItems()){
            Medicine medicine=medicineRepository.findById(item.getId()).orElseThrow(()->new RuntimeException("Medicine not found"));

            medicine.setQty(medicine.getQty() + item.getQty());

            medicineRepository.save(medicine);

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



}
