package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    List<Category> getAllcategories();
    Category getcategoryById(Long id);
    Category saveCategroy(Category category);
    void deleteCategroy(Long id);
}
