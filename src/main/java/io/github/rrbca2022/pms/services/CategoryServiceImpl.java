package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Category;
import io.github.rrbca2022.pms.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository){this.categoryRepository=categoryRepository;}


    @Override
    public List<Category> getAllcategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getcategoryById(Long id) {
        return  categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category saveCategroy(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategroy(Long id) {
        categoryRepository.deleteById(id);
    }
}
