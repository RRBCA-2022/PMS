package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.dto.SaleFormDTO;
import io.github.rrbca2022.pms.services.CategoryService;
import io.github.rrbca2022.pms.services.MedicineService;
import io.github.rrbca2022.pms.services.SalesService;
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
@RequestMapping("/sales")
public class SalesController {


    private final MedicineService medicineService;
    private final CategoryService categoryService;
    private final SalesService salesService;


    @GetMapping
    public String sales(HttpSession session,  ModelMap model) {
        model.addAttribute("medicines", medicineService.getAllMedicines());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("sales",salesService.getAll());
        model.addAttribute("formDTO", new SaleFormDTO());
        return "sales";
    }

    @PostMapping("/sell")
    public String sell(SaleFormDTO formDTO, RedirectAttributes redirectAttributes) {
        try {
            double total = salesService.processSale(formDTO);

            redirectAttributes.addFlashAttribute(
                    "toastMessage",
                    "Sale of Rs. " + total + " completed successfully"
            );
            redirectAttributes.addFlashAttribute("toastType", "success");

        } catch (RuntimeException ex) {

            redirectAttributes.addFlashAttribute(
                    "toastMessage",
                    ex.getMessage()
            );
            redirectAttributes.addFlashAttribute("toastType", "error");
        }



        return "redirect:/sales";
    }



}
