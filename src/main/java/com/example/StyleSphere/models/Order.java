package com.example.StyleSphere.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // Escape the table name

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;


    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderQuantity> quantities = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser localUser) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    public List<OrderQuantity> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<OrderQuantity> quantities) {
        this.quantities = quantities;
    }
}
