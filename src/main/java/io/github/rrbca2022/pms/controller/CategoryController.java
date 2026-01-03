package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/category")
public class CategoryController {

	private  final CategoryService categoryService;

	public CategoryController(CategoryService categoryService){this.categoryService=categoryService;}

	@GetMapping
	public String listCategories(Model model){
		model.addAttribute("categories", categoryService.getAllcategories());
		return "category" ;
	}

	@GetMapping("/new")
	public String newCategory(Model model){
		model.addAttribute("category", new Category());
		return "add_category" ;
	}

	@PostMapping("/save")
	public String saveCategory(@ModelAttribute Category category){
		categoryService.saveCategroy(category);
		return "redirect:/category" ;
	}

	@GetMapping("/edit/{id}")
	public String editCategory(@PathVariable Long id,Model model){
		model.addAttribute("category", new Category());
		model.addAttribute("ctg_id",categoryService.getcategoryById(id));
		return "add_category" ;
	}

	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id){
		categoryService.deleteCategroy(id);
		return "redirect:/category" ;
	}


}
