package com.example.shop_app.controller;

import com.example.shop_app.dto.CategoryDTO;
import com.example.shop_app.model.Category;
import com.example.shop_app.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/listCategories")
    public ResponseEntity<?> getCategories(
            @RequestParam("page") int page,
            @RequestParam("size") int size )
    {
     List<Category> categories =    categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }
    @PostMapping("/addCategory")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
             BindingResult result
    ) {
        if (result.hasErrors()) {
           List<String> errorMessages= result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Category added: " + categoryDTO.getName());
    }
    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @RequestBody   CategoryDTO categoryDTO
    ) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Updated category successfully with ID: " + id);
    }
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("CategoryID: " + id + " delete successfully");
    }


}
