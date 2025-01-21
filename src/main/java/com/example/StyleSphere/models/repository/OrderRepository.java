package com.example.StyleSphere.models.repository;

import com.example.StyleSphere.models.Order;
import com.example.StyleSphere.models.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepository extends ListCrudRepository<Order, Long> {
    List<Order> findByUser(LocalUser user);
}
