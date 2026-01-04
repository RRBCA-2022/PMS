package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    private final CategoryService categoryService;
    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository, CategoryService categoryService){
        this.medicineRepository=medicineRepository;
        this.categoryService=categoryService;
    }

    public List<Medicine> getAllMedicines(){return medicineRepository.findAll();}

    public Medicine getMedicineById(Long id){return medicineRepository.findById(id).orElse(null);}

    public  void savaMedicine(Medicine medicine,Long id)
    {
        Category category=categoryService.getCategoryById(id);
        medicine.setCategory(category);
        medicineRepository.save(medicine);
    }

    public void  deleteMedicine(Long id){ medicineRepository.deleteById(id);}




}
