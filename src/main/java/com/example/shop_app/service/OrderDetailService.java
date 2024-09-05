package com.example.shop_app.service;

import com.example.shop_app.dto.OrderDetailDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.Order;
import com.example.shop_app.model.OrderDetail;
import com.example.shop_app.model.Product;
import com.example.shop_app.repo.OrderDetailRepo;
import com.example.shop_app.repo.OrderRepo;
import com.example.shop_app.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{
    private final OrderDetailRepo orderDetailRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        //liem tra xem order co ton tai khong
        Order order = orderRepo.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found with id: " + orderDetailDTO.getOrderId()));
        //tim product
        Product product = productRepo.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + orderDetailDTO.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        orderDetailRepo.save(orderDetail);

        return orderDetailRepo.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(Long id) throws DataNotFoundException {
        return orderDetailRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: " + id));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        //kiem tra xem order detail co ton tai khong
        OrderDetail orderDetail = orderDetailRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: " + id));
        Order order = orderRepo.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found with id: " + orderDetailDTO.getOrderId()));
        Product product = productRepo.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + orderDetailDTO.getProductId()));
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        orderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        orderDetail.setColor(orderDetailDTO.getColor());
        return orderDetailRepo.save(orderDetail);


    }

    @Override
    public void deleteOrderDetail(Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: " + id));
        orderDetailRepo.deleteById(id);

    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {

        return orderDetailRepo.findByOrderId(orderId);
    }
}
