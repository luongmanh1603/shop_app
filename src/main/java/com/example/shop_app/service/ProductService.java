package com.example.shop_app.service;

import com.example.shop_app.dto.ProductDTO;
import com.example.shop_app.dto.ProductImageDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.exception.InvalidParamException;
import com.example.shop_app.model.Category;
import com.example.shop_app.model.Product;
import com.example.shop_app.model.ProductImage;
import com.example.shop_app.repo.CategoryRepo;
import com.example.shop_app.repo.ProductImageRepo;
import com.example.shop_app.repo.ProductRepo;
import com.example.shop_app.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ProductImageRepo productImageRepo;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
          Category existingCategory =   categoryRepo.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find category with id: " + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .build();
        return productRepo.save(newProduct) ;
    }

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {
        return productRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {

        return productRepo.findAll(pageRequest).map(ProductResponse::fromEntity );
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + id));
        if (existingProduct != null) {
              Category existingCategory = categoryRepo.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new DataNotFoundException("Cannot find category with id: " + productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setCategory(existingCategory);
            return productRepo.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) throws DataNotFoundException {
        Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isPresent()) {
            productRepo.deleteById(id);
        } else {
            throw new DataNotFoundException("Cannot find product with id: " + id);
        }

    }

    @Override
    public boolean existsByName(String name) {

        return productRepo.existsByName(name);
    }


    @Override
    public ProductImage createProductImage(
            Long  productId,
            ProductImageDTO productImageDTO) throws DataNotFoundException {
        Product existingProduct = productRepo.findById(productImageDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //khong cho create qua 5 anh
         int size  = productImageRepo.findAllByProductId(productId).size();
        if (size >= 5) {
            throw new InvalidParamException("Cannot more than 5 images for a product");
        }


        return productImageRepo.save(newProductImage);
    }
}
