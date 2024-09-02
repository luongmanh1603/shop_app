package com.example.shop_app.service;

import com.example.shop_app.dto.CategoryDTO;
import com.example.shop_app.dto.OrderDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.Category;
import com.example.shop_app.model.Order;
import com.example.shop_app.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    Order getOrderById(Long id) throws DataNotFoundException;
    List<Order> getOrdersByUserId(Long userId);
    Order updateOrder(Long id,OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id) throws DataNotFoundException;
}
