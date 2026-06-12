package com.ecommerce.order_service.service;

import com.ecommerce.order_service.client.ProductClient;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.exception.InsufficientStockException;
import com.ecommerce.order_service.exception.ProductNotFoundException;
import com.ecommerce.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository,
                        ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public OrderResponse createOrder(OrderRequest request, String email) {

        System.out.println("Order created by: " + email);

        // Get logged-in user email from JWT
        String userEmail =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        logger.info("Order requested by user={}", userEmail);

        // Fetch product from Product Service
        logger.info("Calling Product Service for productId={}", request.getProductId());

        Map<String, Object> product =
                productClient.getProductById(request.getProductId());

        //Global Exception Handler
        if(product == null){
            logger.error("Product not found for productId={}", request.getProductId());
            throw new ProductNotFoundException("Product not found");
        }

        Integer stock = (Integer) product.get("stock");
        BigDecimal price =
                new BigDecimal(product.get("price").toString());

        if (stock < request.getQuantity()) {
            logger.warn("Insufficient stock for productId={} requested={} available={}",
                    request.getProductId(), request.getQuantity(), stock);
//            throw new RuntimeException("Insufficient stock");
            throw new InsufficientStockException("Insufficient stock");
        }

        logger.info("Reducing stock in Product Service productId={} quantity={}",
                request.getProductId(), request.getQuantity());

        // Reduce stock in Product Service
        productClient.reduceStock(
                request.getProductId(),
                request.getQuantity()
        );

        // Create order
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setUserEmail(userEmail);
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(
                price.multiply(
                        BigDecimal.valueOf(request.getQuantity())
                )
        );

        Order saved = orderRepository.save(order);

        logger.info("Order successfully created orderId={} user={} productId={}",
                saved.getId(), userEmail, request.getProductId());

        // Return response
        return mapToResponse(saved);
    }

    public List<OrderResponse> getOrdersByUser(String email) {

        List<Order> orders =
                orderRepository.findByUserEmail(email);

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setProductId(order.getProductId());
        response.setQuantity(order.getQuantity());
        response.setTotalPrice(order.getTotalPrice());

        return response;
    }


}

