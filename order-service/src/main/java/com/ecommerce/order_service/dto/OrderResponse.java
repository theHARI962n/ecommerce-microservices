package com.ecommerce.order_service.dto;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public class OrderResponse {


    private UUID orderId;


    private String userEmail;


    private BigDecimal totalPrice;


    private List<OrderItemResponse> items;



    public UUID getOrderId() {
        return orderId;
    }


    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }


    public String getUserEmail() {
        return userEmail;
    }


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }


    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    public List<OrderItemResponse> getItems() {
        return items;
    }


    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}

//package com.ecommerce.order_service.dto;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//public class OrderResponse {
//
//    private UUID orderId;
//    private UUID productId;
//    private Integer quantity;
//    private BigDecimal totalPrice;
//
//    public UUID getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(UUID orderId) {
//        this.orderId = orderId;
//    }
//
//    public UUID getProductId() {
//        return productId;
//    }
//
//    public void setProductId(UUID productId) {
//        this.productId = productId;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//}
