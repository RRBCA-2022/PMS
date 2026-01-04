package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class MedicineService {

    private CategoryService categoryService;
    private MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository){this.medicineRepository=medicineRepository;}

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
