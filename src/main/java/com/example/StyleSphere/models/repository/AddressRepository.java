package com.example.StyleSphere.models.repository;

import com.example.StyleSphere.models.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AddressRepository extends ListCrudRepository<Address, Long> {
    List<Address> findByUser_Id(Long id);
}
