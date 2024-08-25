package com.example.shop_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order ID must be greater than 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be greater than 0")
    private Long productId;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private Float price;

    @Min(value = 1, message = "Quantity must be greater than 0")
    @JsonProperty("number_of_products")
    private Integer numberOfProducts;

    @Min(value = 0, message = "Total money must be greater than 0")
    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;

}
