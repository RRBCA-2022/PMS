package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor()
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(String id) {
        return  categoryRepository.findById(id).orElse(null);
    }

    public Category getCategoryByName(String name) { return categoryRepository.findByName(name).orElse(null); }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
