package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.PurchasesRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchasesService {
    private final MedicineRepository medicineRepository;
    private final PurchasesRepository purchasesRepository;
    public PurchasesService(MedicineRepository medicineRepository, PurchasesRepository purchasesRepository){
        this.medicineRepository = medicineRepository;
        this.purchasesRepository = purchasesRepository;
    }


}
