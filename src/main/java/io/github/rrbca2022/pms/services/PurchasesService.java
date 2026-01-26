package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.dto.MedItemDTO;
import io.github.rrbca2022.pms.dto.PurchaseFormDTO;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.Purchase;
import io.github.rrbca2022.pms.entity.PurchaseItem;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.PurchasesRepository;
import io.github.rrbca2022.pms.repository.SuppliersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Service
public class PurchasesService {
    private final MedicineRepository medicineRepository;
    private final PurchasesRepository purchasesRepository;
    private final SuppliersRepository suppliersRepository;

    public double newPurchase(PurchaseFormDTO purchaseDTO) {

        //create new purchase
        Purchase purchase= new Purchase();
        purchase.setDatetime(purchaseDTO.getOrderDate().atTime(LocalTime.now()));
        purchase.setSupplierName(purchaseDTO.getSupplierName());

        if(purchaseDTO.getSupplierId()!=null){
            purchase.setSupplier(suppliersRepository.findById(purchaseDTO.getSupplierId()).orElse(null));

        }
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
