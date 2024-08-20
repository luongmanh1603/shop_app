package com.example.shop_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping("/listCategories")
    public ResponseEntity<String> getCategories() {
        return ResponseEntity.ok("List of categories");
    }
    @PostMapping("/addCategory")
    public ResponseEntity<String> addCategory() {
        return ResponseEntity.ok("Category added");
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
