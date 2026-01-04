package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Medicine;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

    @GetMapping("/new")
    public String newMedicine(Model model){
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("mode","Add");
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add_medicine";
    }

    @PostMapping("/save")
    public String saveMedicine(@ModelAttribute Medicine medicine,
                               @RequestParam("id") Long id){
        medicineService.savaMedicine(medicine,id);
        return "redirect:/medicine";
    }

    @GetMapping("/edit/{id}")
    public String editMedicine(@PathVariable Long id, ModelMap model){
        Medicine med=medicineService.getMedicineById(id);
        model.addAttribute("medicine",med);
        model.addAttribute("mode","Edit");
        return "add_medicine";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id){
        medicineService.deleteMedicine(id);
        return "redirect:/medicine";
    }

}
