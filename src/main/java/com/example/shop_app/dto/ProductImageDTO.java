package com.example.shop_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    @Min(value = 1, message = "Product ID must be greater than 0")
    @JsonProperty("product_id")
    private Long productId;

    @Size(min = 3, max = 300, message = "Image URL must be between 3 and 300 characters")
    @JsonProperty("image_url")
    private String imageUrl;

}
