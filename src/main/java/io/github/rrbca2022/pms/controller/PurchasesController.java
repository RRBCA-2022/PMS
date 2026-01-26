package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.dto.PurchaseFormDTO;
import io.github.rrbca2022.pms.dto.MedItemDTO;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import io.github.rrbca2022.pms.services.PurchasesService;
import io.github.rrbca2022.pms.services.SuppliersService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return "purchases";
    }

    @PostMapping("/save")
    public String save(PurchaseFormDTO purchaseForm, RedirectAttributes redirectAttributes) {

//        Supplier supplier;
//
//        if (purchaseForm.getSupplierId() != null) {
//            // user selected from dropdown
//            supplier = supplierService.findById(purchaseForm.getSupplierId());
//        } else {
//            // user typed manually; try to fetch by name or create new
//            supplier = supplierService.findByName(purchaseForm.getSupplierName());
//            if (supplier == null) {
//                supplier = new Supplier();
//                supplier.setName(purchaseForm.getSupplierName());
//                supplierService.save(supplier); // create new supplier
//            }
//        }
            // ✅ delegate everything to service
            double total = purchasesService.newPurchase(purchaseForm);

            redirectAttributes.addFlashAttribute(
                    "toastMessage",
                    "Purchase of " + total + " completed successfully"
            );
            redirectAttributes.addFlashAttribute("toastType", "success");

            return "redirect:/purchases";
        }
    }




