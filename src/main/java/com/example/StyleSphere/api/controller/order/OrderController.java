package com.example.StyleSphere.api.controller.order;

import com.example.StyleSphere.models.LocalUser;
import com.example.StyleSphere.models.Order;
import com.example.StyleSphere.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders(@AuthenticationPrincipal LocalUser user){
        return  orderService.getOrders(user);
    }
}
