package com.example.shop_app.service;

import com.example.shop_app.dto.CategoryDTO;
import com.example.shop_app.model.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id,CategoryDTO categoryDTO);
    void deleteCategory(Long id);


}
