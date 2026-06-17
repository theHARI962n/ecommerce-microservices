package com.ecommerce.cart_service.dto;


import java.util.List;


public class OrderRequest {


    private List<OrderItemRequest> items;


    public List<OrderItemRequest> getItems() {
        return items;
    }


    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

}