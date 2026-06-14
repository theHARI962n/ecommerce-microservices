package com.ecommerce.order_service.dto;


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


//package com.ecommerce.order_service.dto;
//
//import java.util.UUID;
//
//public class OrderRequest {
//    private UUID productId;
//    private Integer quantity;
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
//}
