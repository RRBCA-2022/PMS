package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.dto.MedItemDTO;
import io.github.rrbca2022.pms.dto.SaleFormDTO;
import io.github.rrbca2022.pms.entity.*;
import io.github.rrbca2022.pms.repository.MedicineBatchRepository;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.SalesRepository;
import io.github.rrbca2022.pms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SalesService {

    private final MedicineRepository medicineRepository;
    private final SalesRepository salesRepository;
    private final UserRepository userRepository;
    private final MedicineBatchRepository batchRepository;

    public List<Sale> getAll() {
        return salesRepository.findAll();
    }

    public double processSale(SaleFormDTO formDTO){
        User seller=userRepository.findById(formDTO.getSellerId()).orElseThrow(()->new RuntimeException("seller not available"));

        Sale sale=new Sale();
        sale.setTimestamp(
                formDTO.getTimestamp() !=null
                ? formDTO.getTimestamp().atTime(LocalTime.now())
                        : LocalDateTime.now()
        );
        sale.setSeller(seller);
        sale.setSellerName(formDTO.getSellerName());

        double total=0.0;

        for(MedItemDTO item:formDTO.getItems()){
            Medicine medicine=medicineRepository.findById(item.getId()).orElseThrow(()->new RuntimeException("medicine not available"));

            if(medicine.getQty()< item.getQty()){
                throw new RuntimeException(
                        "medicine not available" + medicine.getName()
                );
            }
            //FEFO Logic: Fetch batches ordered by expiry date
            List<MedicineBatch> batches = batchRepository.findAvailableBatchesFEFO(medicine.getId());
            int remainingToDeduct = item.getQty();

            for (MedicineBatch batch : batches) {
                if (remainingToDeduct <= 0) break;

                int batchQty = batch.getStockQuantity();
                if (batchQty >= remainingToDeduct) {
                    // Current batch can cover the remaining need
                    batch.setStockQuantity(batchQty - remainingToDeduct);
                    remainingToDeduct = 0;
                } else {
                    // Drain this batch and move to the next
                    remainingToDeduct -= batchQty;
                    batch.setStockQuantity(0);
                }
                batchRepository.save(batch);
            }

            medicine.setQty(medicine.getQty()-item.getQty());

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setMedicine(medicine);
            saleItem.setMedicineName(medicine.getName());
            saleItem.setQuantity(item.getQty());
            saleItem.setPrice(medicine.getPrice());

            sale.getItems().add(saleItem);

            total += medicine.getPrice() * item.getQty();

        }
        sale.setTotalAmount(total);
        salesRepository.save(sale);

        return total;

    }


}
