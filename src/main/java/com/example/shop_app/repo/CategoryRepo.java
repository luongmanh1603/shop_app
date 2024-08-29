package com.example.shop_app.repo;

import com.example.shop_app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo  extends JpaRepository<Category, Long> {
}
