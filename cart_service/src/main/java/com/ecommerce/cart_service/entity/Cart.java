package com.ecommerce.cart_service.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name="carts")
public class Cart {


    @Id
    @GeneratedValue
    private UUID id;


    private String userEmail;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;



    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> items = new ArrayList<>();



    @PrePersist
    public void onCreate(){

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

    }
    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    public UUID getId() {
        return id;
    }


    public String getUserEmail() {
        return userEmail;
    }


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public List<CartItem> getItems() {
        return items;
    }


    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}