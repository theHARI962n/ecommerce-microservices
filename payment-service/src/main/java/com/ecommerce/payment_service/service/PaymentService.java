package com.ecommerce.payment_service.service;


import com.ecommerce.payment_service.dto.PaymentRequest;
import com.ecommerce.payment_service.dto.PaymentResponse;
import com.ecommerce.payment_service.entity.Payment;
import com.ecommerce.payment_service.repository.PaymentRepository;

import org.springframework.stereotype.Service;


@Service
public class PaymentService {


    private final PaymentRepository paymentRepository;



    public PaymentService(
            PaymentRepository paymentRepository
    ){

        this.paymentRepository = paymentRepository;

    }



    public PaymentResponse createPayment(
            PaymentRequest request,
            String email
    ){


        Payment payment = new Payment();


        payment.setOrderId(
                request.getOrderId()
        );


        payment.setUserEmail(email);


        payment.setAmount(
                request.getAmount()
        );


        /*
          Later:
          Razorpay order creation here
        */


        payment.setStatus("CREATED");



        Payment saved =
                paymentRepository.save(payment);



        PaymentResponse response =
                new PaymentResponse();



        response.setPaymentId(
                saved.getId()
        );


        response.setStatus(
                saved.getStatus()
        );


        return response;

    }

}