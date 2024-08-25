package com.example.shop_app.controller;

import com.example.shop_app.dto.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
public class CategoryController {

    @GetMapping("/listCategories")
    public ResponseEntity<String> getCategories(
            @RequestParam("page") int page,
            @RequestParam("size") int size )
    {

        return ResponseEntity.ok("List of categories in page: " + page + " with size: " + size);
    }
    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(
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
        return ResponseEntity.ok("Category added: " + categoryDTO.getName());
    }
    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("CategoryID: " + id + " updated");
    }
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("CategoryID: " + id + " deleted");
    }


}
