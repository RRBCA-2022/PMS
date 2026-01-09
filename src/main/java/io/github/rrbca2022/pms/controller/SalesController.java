package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.dto.MedItemDTO;
import io.github.rrbca2022.pms.dto.SaleFormDTO;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import io.github.rrbca2022.pms.services.SalesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String sales(HttpSession session,  ModelMap model) {
        model.addAttribute("medicines", medicineService.getAllMedicines());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "sales";
    }

    @PostMapping("/sell")
    public String sell(SaleFormDTO saleForm, RedirectAttributes redirectAttributes) {

        System.out.println(saleForm.getSellerId());
        System.out.println(saleForm.getSellerName());
        System.out.println(saleForm.getTimestamp());

        double total = 0;

        for (MedItemDTO item : saleForm.getItems()) {
            total += item.getPrice();
            System.out.println("Medicine ID: " + item.getId() +
                    ", Name: " + item.getName() +
                    ", Price: " + item.getPrice() +
                    ", Qty: " + item.getQty());
        }

        redirectAttributes.addFlashAttribute("toastMessage", "Sale of " + total + " completed successfully");
        redirectAttributes.addFlashAttribute("toastType", "success");

        return "redirect:/sales";
    }



}
