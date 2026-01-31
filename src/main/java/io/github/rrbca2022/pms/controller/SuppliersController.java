package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Supplier;
import io.github.rrbca2022.pms.services.SuppliersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;



@Controller
@AllArgsConstructor
@RequestMapping("/suppliers")
public class SuppliersController {

    private final SuppliersService suppliersService;

    @GetMapping
    public String ListSuppliers(Model model){
        model.addAttribute("suppliers", suppliersService.getAllSuppliers());
        return "suppliers";
    }

    @GetMapping("/new")
    public String newSupplier(Model model){
        model.addAttribute("supplier", new Supplier());
        model.addAttribute("mode","Add");
        return "edit_supplier";
    }

    @PostMapping("/save")
    public String savaSupplier(@ModelAttribute Supplier supplier){
        suppliersService.saveSupplier(supplier);
        return "redirect:/suppliers";
    }
    @GetMapping("/edit/{id}")
    public String editSupplier(@PathVariable String id, ModelMap model){
        Supplier sup = suppliersService.getSupplierById(id);
        model.addAttribute("supplier",sup);
        model.addAttribute("mode","Edit");
        return "edit_supplier";

    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable String id){
        suppliersService.deleteSupplier(id);
        return "redirect:/suppliers";
    }


}
