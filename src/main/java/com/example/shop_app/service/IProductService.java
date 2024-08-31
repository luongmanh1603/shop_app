package com.example.shop_app.service;

import com.example.shop_app.dto.ProductDTO;
import com.example.shop_app.dto.ProductImageDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.Product;
import com.example.shop_app.model.ProductImage;
import com.example.shop_app.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
    Product getProductById(Long id) throws DataNotFoundException;
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    Product updateProduct(Long id,ProductDTO productDTO) throws DataNotFoundException;
    void deleteProduct(Long id) throws DataNotFoundException;
    boolean existsByName(String name);
    public ProductImage createProductImage(Long  productId, ProductImageDTO productImageDTO) throws DataNotFoundException;

}
