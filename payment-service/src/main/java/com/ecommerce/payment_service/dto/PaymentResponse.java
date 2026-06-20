package com.ecommerce.payment_service.dto;


import java.util.UUID;


public class PaymentResponse {


    private UUID paymentId;


    private String status;



    public UUID getPaymentId() {
        return paymentId;
    }


    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}