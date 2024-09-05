package com.example.shop_app.controller;

import com.example.shop_app.dto.OrderDetailDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.OrderDetail;
import com.example.shop_app.responses.OrderDetailResponse;
import com.example.shop_app.service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @GetMapping("/getOrderDetail/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id
    ){
        try {
            OrderDetail orderDetail =  orderDetailService.getOrderDetailById(id);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
            List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                    .map(OrderDetailResponse::fromOrderDetail)
                    .toList();
            return ResponseEntity.ok(orderDetailResponses);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }



    @PostMapping("/addOrderDetail")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {

        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(newOrderDetail));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/updateOrderDetail/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDetailDTO neworderDetailDTO
    ) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, neworderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @DeleteMapping("/deleteOrderDetail/{id}")
    public ResponseEntity<?> deleteOrderDetail(
            @Valid @PathVariable long id
    ) {
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok("Order detail deleted with id: " + id);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }



}
