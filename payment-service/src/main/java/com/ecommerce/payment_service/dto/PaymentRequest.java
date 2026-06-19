package com.ecommerce.payment_service.dto;


import java.math.BigDecimal;
import java.util.UUID;


public class PaymentRequest {


    private UUID orderId;


    private BigDecimal amount;



    public UUID getOrderId() {
        return orderId;
    }


    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}