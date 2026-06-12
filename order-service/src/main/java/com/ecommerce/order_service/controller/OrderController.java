package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders/")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("X-User-Email") String email
    ) {

        System.out.println("Order placed by: " + email);

        return ResponseEntity.ok(orderService.createOrder(request, email));
    }

    @GetMapping
    public ResponseEntity<?> getMyOrders(
            @RequestHeader("X-User-Email") String email
    ) {
        return ResponseEntity.ok(orderService.getOrdersByUser(email));
    }
}
