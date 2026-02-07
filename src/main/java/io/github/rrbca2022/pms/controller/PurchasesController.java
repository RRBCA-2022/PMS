package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.dto.PurchaseFormDTO;
import io.github.rrbca2022.pms.entity.Purchase;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import io.github.rrbca2022.pms.services.PurchasesService;
import io.github.rrbca2022.pms.services.SuppliersService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/purchases")
public class PurchasesController {

    private final MedicineService medicineService;
    private final CategoryService categoryService;
    private final PurchasesService purchasesService;
    private final SuppliersService suppliersService;

    @GetMapping
    public String purchases (HttpSession session,ModelMap model) {
        model.addAttribute("mode","Add");
        model.addAttribute("medicines", medicineService.getAllMedicines());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", suppliersService.getAllSuppliers());
        model.addAttribute("purchases", purchasesService.getAllPurchases());
        return "purchases";
    }
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Purchase> getPurchaseDetails(@PathVariable String id) {
        try {
            Purchase purchase = purchasesService.getPurchaseById(id);
            return ResponseEntity.ok(purchase);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public String save(PurchaseFormDTO purchaseForm, RedirectAttributes redirectAttributes) {

            double total = purchasesService.newPurchase(purchaseForm);

            redirectAttributes.addFlashAttribute(
                    "toastMessage",
                    "Purchase of " + total + " completed successfully"
            );
            redirectAttributes.addFlashAttribute("toastType", "success");

            return "redirect:/purchases";
    }

    @PostMapping("/review/{id}")
    public String review(
            @PathVariable String id,
            @RequestParam String status,
            RedirectAttributes redirectAttributes
    ) {
        //purchaseService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("message", "Status updated to " + status);
        return "redirect:/purchases";
    }


}




