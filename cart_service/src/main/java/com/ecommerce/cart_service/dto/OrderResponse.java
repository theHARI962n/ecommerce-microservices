package com.ecommerce.cart_service.dto;


import java.math.BigDecimal;
import java.util.UUID;


public class OrderResponse {


    private UUID orderId;


    private BigDecimal totalPrice;


    public UUID getOrderId() {
        return orderId;
    }


    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }


    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}