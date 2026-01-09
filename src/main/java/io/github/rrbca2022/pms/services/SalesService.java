package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.Sale;
import io.github.rrbca2022.pms.entity.SaleItem;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.SalesRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {

    private final MedicineRepository medicineRepository;
    private final SalesRepository salesRepository;
    public SalesService(MedicineRepository medicineRepository,SalesRepository salesRepository){
        this.medicineRepository=medicineRepository;
        this.salesRepository=salesRepository;
    }
    @Data
    public static class SalesDTO{
        private List<Long> medicineId;
        private List<Integer> quantity;
    }


    public void sellMedicine(
            SalesDTO dto){

        List<Long> medicineIds = dto.getMedicineId();
        List<Integer> quantities = dto.getQuantity();

        if (medicineIds.size() != quantities.size()) {
            throw new RuntimeException("Invalid sales data");
        }

        Sale sale=new Sale();
        sale.setTimestamp(LocalDateTime.now());


        double totalAmount=0;
        List<SaleItem> salesItems=new ArrayList<>();

        for(int i=0; i<medicineIds.size(); i++){
            Long medicineId=medicineIds.get(i);
            int qty=quantities.get(i);

            Medicine med=medicineRepository.findById(medicineId).orElseThrow(() ->new RuntimeException("medicine not found"));

            if(med.getQty()<qty){
                throw new RuntimeException("Insufficient stock for "+med.getName());
            }

            med.setQty(med.getQty()-qty);
            medicineRepository.save(med);

            SaleItem saleItem=new SaleItem();
            saleItem.setMedicineName(med.getName());
            saleItem.setQuantity(med.getQty());
            saleItem.setPrice(med.getPrice());
            saleItem.setSale(sale);

            salesItems.add(saleItem);
            totalAmount =totalAmount +med.getPrice() * med.getQty();
        }

        sale.setTotalAmount(totalAmount);
        sale.setItems(salesItems);
        salesRepository.save(sale);

    }


}
