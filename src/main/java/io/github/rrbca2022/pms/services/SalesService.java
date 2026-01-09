package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.repository.SalesRepository;
import org.springframework.stereotype.Service;

@Service
public class SalesService {

    private final MedicineRepository medicineRepository;
    private final SalesRepository salesRepository;
    public SalesService(MedicineRepository medicineRepository,SalesRepository salesRepository){
        this.medicineRepository=medicineRepository;
        this.salesRepository=salesRepository;
    }

}
