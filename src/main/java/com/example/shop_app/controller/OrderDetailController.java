package com.example.shop_app.controller;

import com.example.shop_app.dto.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
public class OrderDetailController {

    @GetMapping("/getOrderDetail/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok("Order detail with id: " + id);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        return ResponseEntity.ok("List order details of order with id: " + orderId);
    }



    @PostMapping("/addOrderDetail")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        return ResponseEntity.ok("Order detail created");
    }
    @PutMapping("/updateOrderDetail/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDetailDTO neworderDetailDTO
    ) {
        return ResponseEntity.ok("Order detail updated: " + id + " with new info: " + neworderDetailDTO);
    }
    @DeleteMapping("/deleteOrderDetail/{id}")
    public ResponseEntity<?> deleteOrderDetail(
            @Valid @PathVariable long id
    ) {
        return ResponseEntity.ok("Order detail with id: " + id + " deleted");
    }



}
