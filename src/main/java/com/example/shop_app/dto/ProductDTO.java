package com.example.shop_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Name cannot be null")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Min(value = 0, message = "Price cannot be less than 0")
    @Max(value = 1000000, message = "Price cannot be more than 10,000,000")
    private Float price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id")
    private Integer categoryId;

    private List<MultipartFile> files;


}
