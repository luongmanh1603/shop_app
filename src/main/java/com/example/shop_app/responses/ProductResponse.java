package com.example.shop_app.responses;

import com.example.shop_app.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends BaseResponse{
        private Long id;
     private String name;
     private Float price;
     private String thumbnail;
     private String description;
     @JsonProperty("category_id")
     private Long categoryId;

     public  static ProductResponse fromEntity(Product product){
          ProductResponse productResponse = ProductResponse.builder()
                  .id(product.getId())
                  .name(product.getName())
                  .price(product.getPrice())
                  .description(product.getDescription())
                  .thumbnail(product.getThumbnail())
                  .categoryId(product.getCategory().getId())
                  .build();
          productResponse.setCreateAt(product.getCreateAt());
          productResponse.setUpdateAt(product.getUpdateAt());
          return productResponse;
     }

}
