package com.example.StyleSphere.models.repository;

import com.example.StyleSphere.models.Product;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductRepository extends ListCrudRepository<Product, Long> {

}
