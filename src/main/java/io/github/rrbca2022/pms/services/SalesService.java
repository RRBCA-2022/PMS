package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.dto.MedItemDTO;
import io.github.rrbca2022.pms.dto.SaleFormDTO;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.Sale;
import io.github.rrbca2022.pms.entity.SaleItem;
import io.github.rrbca2022.pms.entity.User;
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
            medicine.setQty(
                    medicine.getQty()-item.getQty()
            );

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
