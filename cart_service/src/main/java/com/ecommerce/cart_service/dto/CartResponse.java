package com.ecommerce.cart_service.dto;


import java.util.List;
import java.util.UUID;


public class CartResponse {


    private UUID cartId;


    private String userEmail;


    private List<CartItemResponse> items;



    public UUID getCartId() {
        return cartId;
    }


    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }


    public String getUserEmail() {
        return userEmail;
    }


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public List<CartItemResponse> getItems() {
        return items;
    }


    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

}