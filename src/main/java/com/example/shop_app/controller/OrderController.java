package com.example.shop_app.controller;


import com.example.shop_app.dto.OrderDTO;
import com.example.shop_app.model.Order;
import com.example.shop_app.responses.OrderResponse;
import com.example.shop_app.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderDTO orderDTO,
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
              Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok( order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //lay order theo user_id
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrdersByUserId(
                      @Valid @PathVariable("user_id") Long userId
    ) {
        try {
               return ResponseEntity.ok("List orders of user with id: " + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    //cap nhat order theo order_id
    //cong viec cua admin
    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO neworderDTO,
            BindingResult result
    ) {
        return ResponseEntity.ok("Order updated: " + id + " with new info: " + neworderDTO);
    }

    //xoa order theo order_id
    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<?> deleteOrder(
            @Valid @PathVariable Long id
    ) {
        //xoa mem -> cap nhap truogn active = false
        return ResponseEntity.ok("Order deleted: " + id);
    }
}
