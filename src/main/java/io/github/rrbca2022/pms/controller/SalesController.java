package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import io.github.rrbca2022.pms.services.SalesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/sell")
    public String sell(
            @RequestBody SalesService.SalesDTO dto,
            ModelMap model
    ) {

        salesService.sellMedicine(dto);
        model.addAttribute("success", "Sale completed successfully!");
        return "redirect:/sales/sales"; // redirect back to sales page
    }


    @GetMapping("/sales")
    public String sales (ModelMap model) {
        model.addAttribute("mode","Add");
        model.addAttribute("medicines", medicineService.getAllMedicines());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("salesDTO", new SalesService.SalesDTO()); // for form binding
        return "salesModal";
    }

}
