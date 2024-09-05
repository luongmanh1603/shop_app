package com.example.shop_app.service;

import com.example.shop_app.dto.OrderDetailDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    OrderDetail getOrderDetailById(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    void deleteOrderDetail(Long id) throws DataNotFoundException;
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
