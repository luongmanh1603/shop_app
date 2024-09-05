package com.example.shop_app.responses;

import com.example.shop_app.model.Order;
import com.example.shop_app.model.OrderDetail;
import com.example.shop_app.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;

    @JsonProperty( "order_id")
    private Long orderId;

    @JsonProperty( "product_id")
    private Long productId;

    @JsonProperty("number_of_products")
    private Integer numberOfProducts;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;

    public  static  OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
      return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .price(orderDetail.getPrice())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();

    }
}
