package com.ecommerce.payment_service.controller;


import com.ecommerce.payment_service.dto.PaymentRequest;
import com.ecommerce.payment_service.dto.PaymentResponse;
import com.ecommerce.payment_service.service.PaymentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/payments")
public class PaymentController {


    private final PaymentService paymentService;



    public PaymentController(
            PaymentService paymentService
    ){

        this.paymentService = paymentService;

    }



    @PostMapping
    public ResponseEntity<?> createPayment(


            @RequestBody PaymentRequest request,


            @RequestHeader("X-User-Email")
            String email


    ){


        PaymentResponse response =
                paymentService.createPayment(
                        request,
                        email
                );


        return ResponseEntity.ok(response);

    }

}