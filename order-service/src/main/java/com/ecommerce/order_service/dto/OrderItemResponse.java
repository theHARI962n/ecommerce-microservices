package com.ecommerce.order_service.dto;


import java.math.BigDecimal;
import java.util.UUID;


public class OrderItemResponse {


    private UUID productId;


    private Integer quantity;


    private BigDecimal price;



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


    public BigDecimal getPrice() {
        return price;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}