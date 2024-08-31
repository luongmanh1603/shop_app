package com.example.shop_app.responses;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListProductResponse {
    private List<ProductResponse> products;
    private Integer totalPages;

}
