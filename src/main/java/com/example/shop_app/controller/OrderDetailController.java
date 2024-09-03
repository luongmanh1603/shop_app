package com.example.shop_app.controller;

import com.example.shop_app.dto.OrderDetailDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.OrderDetail;
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
    ) throws DataNotFoundException {
      OrderDetail orderDetail =  orderDetailService.getOrderDetailById(id);
        return ResponseEntity.ok(orderDetail);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(orderDetails);
    }



    @PostMapping("/addOrderDetail")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {

        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(newOrderDetail);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
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
