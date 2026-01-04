package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Supplier;
import io.github.rrbca2022.pms.services.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;
    public SupplierController(SupplierService supplierService) {this.supplierService=supplierService;}

    @GetMapping
    public String  ListSuppliers(Model model){
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "supplier";
    }

    @GetMapping("/new")
    public String newSupplier(Model model){
        model.addAttribute("supplier", new Supplier());
        model.addAttribute("mode","Add");
        return "add_supplier";
    }

    @PostMapping("/save")
    public String savaSupplier(@ModelAttribute Supplier supplier){
        supplierService.saveSupplier(supplier);
        return "redirect:/supplier";
    }
    @GetMapping("/edit/{id}")
    public String editSupplier(@PathVariable Long id, ModelMap model){
        Supplier supplier = supplierService.getSupplierById(id);
        model.addAttribute("medicine",supplier);
        model.addAttribute("mode","Edit");
        return "add_supplier";

    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id){
        supplierService.deleteSupplier(id);
        return "redirect:/supplier";
    }


}
