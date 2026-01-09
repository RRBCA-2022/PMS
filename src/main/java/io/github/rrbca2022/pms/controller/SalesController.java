package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import io.github.rrbca2022.pms.services.SalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales")
public class SalesController {


    private final MedicineService medicineService;
    private final CategoryService categoryService;
    private final SalesService salesService;

    public SalesController(SalesService salesService,MedicineService medicineService, CategoryService categoryService){
        this.salesService=salesService;
        this.medicineService = medicineService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String sales(ModelMap model) {
        model.addAttribute("mode","Add");
        model.addAttribute("medicines", medicineService.getAllMedicines());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("salesDTO", new SalesService.SalesDTO()); // for form binding
        return "sales";
    }

    @PostMapping("/sell")
    public String sell(
            @RequestBody SalesService.SalesDTO dto,
            ModelMap model
    ) {

        salesService.sellMedicine(dto);
        model.addAttribute("saleSuccess", "Sale completed successfully!");
        return "redirect:/sales"; // redirect back to sales page
    }



}
