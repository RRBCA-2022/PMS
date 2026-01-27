package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

	private  final CategoryService categoryService;

	@GetMapping
	public String listCategories(Model model){
		model.addAttribute("categories", categoryService.getAllCategories());
		return "category" ;
	}

	@GetMapping("/new")
	public String newCategory(Model model){
		model.addAttribute("category", new Category());
		model.addAttribute("mode", "Add");
		return "add_category" ;
	}

	@PostMapping("/save")
	public String saveCategory(@ModelAttribute Category category){
		categoryService.saveCategory(category);
		return "redirect:/category" ;
	}

	@GetMapping("/edit/{id}")
	public String editCategory(@PathVariable Long id, ModelMap model){
		Category ctg = categoryService.getCategoryById(id);
		model.addAttribute("category", ctg);
		model.addAttribute("mode", "Edit");
		return "add_category" ;
	}

	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id){
		categoryService.deleteCategory(id);
		return "redirect:/category" ;
	}


}