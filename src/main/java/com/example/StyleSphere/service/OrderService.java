package com.example.StyleSphere.service;

import com.example.StyleSphere.models.Order;
import com.example.StyleSphere.models.LocalUser;
import com.example.StyleSphere.models.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<Order> getOrders(LocalUser user){
        return orderRepository.findByUser(user);
    }
}
