package com.ecommerce.cart_service.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name="cart_items")
public class CartItem {


    @Id
    @GeneratedValue
    private UUID id;


    private UUID productId;


    private Integer quantity;



    @ManyToOne
    @JoinColumn(name="cart_id")
    @JsonIgnore
    private Cart cart;



    public UUID getId() {
        return id;
    }


    public UUID getProductId() {
        return productId;
    }


    public void setProductId(UUID productId) {
        this.productId = productId;
    }


    public Integer getQuantity() {
        return quantity;
    }


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Cart getCart() {
        return cart;
    }


    public void setCart(Cart cart) {
        this.cart = cart;
    }
}