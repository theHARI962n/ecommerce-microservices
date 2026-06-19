package com.ecommerce.payment_service.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name="payments")
public class Payment {


    @Id
    @GeneratedValue
    private UUID id;


    private UUID orderId;


    private String userEmail;


    private BigDecimal amount;


    private String status;


    private String paymentId;


    private LocalDateTime createdAt;



    @PrePersist
    public void onCreate(){

        createdAt = LocalDateTime.now();

    }



    public UUID getId() {
        return id;
    }


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


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getPaymentId() {
        return paymentId;
    }


    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}