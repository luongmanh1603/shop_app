package com.example.shop_app.service;

import com.example.shop_app.dto.OrderDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.Order;
import com.example.shop_app.model.OrderStatus;
import com.example.shop_app.model.User;
import com.example.shop_app.repo.OrderRepo;
import com.example.shop_app.repo.UserRepo;
import com.example.shop_app.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;


    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        //kiem tra user co ton tai khong
          User user = userRepo
                  .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found:" + orderDTO.getUserId()));
          //convert orderDTO -> order
         //dung thu vien modelmapper
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        //kiem tra shipping date
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be in the future");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepo.save(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) throws DataNotFoundException {
        return orderRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found:" + id));
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {

        return orderRepo.findByUserId(userId);
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found:" + id));
        User existingUser = userRepo.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found:" + orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, existingOrder);
        existingOrder.setUser(existingUser);
        //check status


        return orderRepo.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) throws DataNotFoundException {
        //khong xoa order ma chi set active = false
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found:" + id));
        if (order != null) {
            order.setActive(false);
            orderRepo.save(order);
        } else {
            throw new DataNotFoundException("Order not found:" + id);

        }

    }
}
