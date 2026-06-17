package com.ecommerce.cart_service.client;


import com.ecommerce.cart_service.dto.OrderRequest;
import com.ecommerce.cart_service.dto.OrderResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(
        name="order-service",
        url="${order.service.url}"
)
public interface OrderClient {


    @PostMapping("/api/orders/")
    OrderResponse createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("X-User-Email") String email
    );

}