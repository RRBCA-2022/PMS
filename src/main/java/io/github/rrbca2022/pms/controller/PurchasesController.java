package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.dto.PurchaseFormDTO;
import io.github.rrbca2022.pms.dto.PurchaseItemDTO;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import io.github.rrbca2022.pms.services.PurchasesService;
import io.github.rrbca2022.pms.services.SuppliersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchases")
public class PurchasesController {

    private final MedicineService medicineService;
    private final CategoryService categoryService;
    private final PurchasesService purchasesService;
    private final SuppliersService suppliersService;

    public PurchasesController(PurchasesService purchasesService, MedicineService medicineService,
                               CategoryService categoryService, SuppliersService suppliersService) {
        this.purchasesService = purchasesService;
        this.medicineService = medicineService;
        this.categoryService = categoryService;
        this.suppliersService = suppliersService;
    }

    @GetMapping
    public String purchases (ModelMap model) {
        model.addAttribute("mode","Add");
        model.addAttribute("medicines", medicineService.getAllMedicines());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", suppliersService.getAllSuppliers());
        return "purchases";
    }

    @PostMapping("/save")
    public String save(PurchaseFormDTO purchaseForm) {
        // supplier and order date
        System.out.println("Supplier: " + purchaseForm.getSupplierName());
        System.out.println("Order Date: " + purchaseForm.getOrderDate());

        // loop through items
        for (PurchaseItemDTO item : purchaseForm.getItems()) {
            System.out.println("Medicine ID: " + item.getId() +
                    ", Name: " + item.getName() +
                    ", Price: " + item.getPrice() +
                    ", Qty: " + item.getQty());
        }

        // call your service to persist
        //purchasesService.savePurchase(purchaseForm);

        return "redirect:/purchases";
    }

}
