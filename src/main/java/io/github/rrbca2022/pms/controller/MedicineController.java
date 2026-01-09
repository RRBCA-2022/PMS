package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medicine")
public class MedicineController {

    private final CategoryService categoryService;
    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService, CategoryService categoryService) {
        this.medicineService=medicineService;
        this.categoryService=categoryService;
    }

    @GetMapping
    public String ListMedicines(Model model){
        model.addAttribute("medicines", medicineService.getAllMedicines());
        return "medicine";
    }

    @PostMapping("/save")
    @ResponseBody
    public Medicine saveMedicine(@Valid @RequestBody Medicine medicine) {

        if (medicine.getCategory() != null && medicine.getCategory().getId() != null) {
            Category cat = categoryService.getCategoryById(medicine.getCategory().getId());
            medicine.setCategory(cat);
        }

        medicineService.saveMedicine(medicine);
        return medicine;
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public Medicine editMedicine(@PathVariable Long id){
        return medicineService.getMedicineById(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id){
        medicineService.deleteMedicine(id);
        return "redirect:/medicine";
    }


}
