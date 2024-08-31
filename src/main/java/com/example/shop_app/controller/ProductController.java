package com.example.shop_app.controller;

import ch.qos.logback.core.util.StringUtil;
import com.example.shop_app.dto.ProductDTO;
import com.example.shop_app.dto.ProductImageDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.Product;
import com.example.shop_app.model.ProductImage;
import com.example.shop_app.responses.ListProductResponse;
import com.example.shop_app.responses.ProductResponse;
import com.example.shop_app.service.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private  final ProductService productService;

    @GetMapping("/listProducts")
    public ResponseEntity<ListProductResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, size,
                Sort.by("createAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        //lay tong so trang
        long totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ListProductResponse.builder()
                .products(products)
                .totalPages((int) totalPages)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);


            return ResponseEntity.ok(ProductResponse.fromEntity(product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
        }
    }

    @PostMapping(value = "/addProduct")
    public ResponseEntity<?> addProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ) {

        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();

                return ResponseEntity.badRequest().body(errorMessages);
            }
               Product newProduct = productService.createProduct(productDTO);




            return ResponseEntity.ok("Product added success: " + productDTO.getName());


        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping(value = "/addProductImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProductImage(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) {
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }

                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File size must be less than 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be in image format");
                }
                //luu file
                String fileName = storeFile(file);
                //luu vao database
                    ProductImage productImage =  productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(fileName)
                                .build()
                );
                productImages.add(productImage);

            }
        } catch (DataNotFoundException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with ID: " + productId);
        }
        return ResponseEntity.ok("Product images added successfully");




    }


    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //them UUID vao ten file dam bao ten file khong trung lap va la duy nhat
        String newFileName = UUID.randomUUID().toString()+ "_" + filename;
        //duong dan den thu muc chua file
        Path uploadDir = Paths.get("uploads");
        //kiem tra thu muc uploadDir co ton tai khong, neu khong thi tao moi
        if (!java.nio.file.Files.exists(uploadDir)) {
            java.nio.file.Files.createDirectories(uploadDir);
        }
        //duong dan den file
        Path destination = Paths.get(uploadDir.toString(), newFileName);
        //luu file
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }


    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id) {
        return ResponseEntity.ok("ProductID: " + id + " updated");
    }
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        try {

            productService.deleteProduct(id);


            return ResponseEntity.ok("Product deleted with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
        }
    }
//    @PostMapping("/generateFakeProducts")
    private ResponseEntity<String> generateFakeProducts() throws DataNotFoundException {
        Faker fake = new Faker();
        for(int i = 0; i < 100; i++) {
            String productName = fake.commerce().productName();
            if (productService.existsByName(productName)) {
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float) fake.number().numberBetween(1000, 1000000))
                    .description(fake.lorem().sentence())
                    .categoryId((long) fake.number().numberBetween(1, 3))
                    .build();
            productService.createProduct(productDTO);
        }

        return ResponseEntity.ok("Fake products generated");
    }
}
