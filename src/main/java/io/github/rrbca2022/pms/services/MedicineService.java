package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.entity.MedicineBatch;
import io.github.rrbca2022.pms.repository.MedicineBatchRepository;
import io.github.rrbca2022.pms.repository.MedicineRepository;
import io.github.rrbca2022.pms.utils.PMSLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor()
public class MedicineService {
    private final CategoryService categoryService;
    private final MedicineRepository medicineRepository;
    private final MedicineBatchRepository batchRepository;

    public List<Medicine> getAllMedicines(){return medicineRepository.findAll();}

    public Medicine getMedicineById(String id){return medicineRepository.findById(id).orElse(null);}

    public Medicine saveMedicine(Medicine medicine) {
        PMSLogger.debug("Saving Medicine: " + medicine.getId() + " - " + medicine.getName());

        // 1. Map Category
        String catId = medicine.getCategory().getId();
        Category category = categoryService.getCategoryById(catId);
        medicine.setCategory(category);

        // 2. Save Medicine (This updates the Medicine table)
        Medicine savedMedicine = medicineRepository.save(medicine);

        // 3. Update or Create Batch (Prevents duplicates and fixes "Out of Stock" status)
        // We look for any existing batch for this medicine ID.
        // Using a custom stream check to find the most relevant batch to update.
        MedicineBatch targetBatch = batchRepository.findAll().stream()
                .filter(b -> b.getMedicine().getId().equals(savedMedicine.getId()))
                .findFirst()
                .orElse(new MedicineBatch());

        targetBatch.setMedicine(savedMedicine);
        targetBatch.setExpiryDate(medicine.getExpDate());
        targetBatch.setStockQuantity(medicine.getQty());

        // Saving with an existing ID updates the record; without an ID, it creates a new one.
        batchRepository.save(targetBatch);

        PMSLogger.debug("Saved Medicine and synchronized Batch for: " + savedMedicine.getName());
        return savedMedicine;
    }

    public void deleteMedicine(String id){ medicineRepository.deleteById(id);}




}
