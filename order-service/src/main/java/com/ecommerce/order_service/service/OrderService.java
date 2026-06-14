package com.ecommerce.order_service.service;

import com.ecommerce.order_service.client.ProductClient;
import com.ecommerce.order_service.dto.OrderItemRequest;
import com.ecommerce.order_service.dto.OrderItemResponse;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.entity.OrderItem;
import com.ecommerce.order_service.exception.InsufficientStockException;
import com.ecommerce.order_service.exception.ProductNotFoundException;
import com.ecommerce.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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


        String userEmail = email;
//                SecurityContextHolder.getContext()
//                        .getAuthentication()
//                        .getName();


        logger.info("Order requested by user={}", userEmail);


        Order order = new Order();

        order.setUserEmail(userEmail);


        BigDecimal totalAmount = BigDecimal.ZERO;


        List<OrderItem> orderItems = new ArrayList<>();


        for(OrderItemRequest itemRequest : request.getItems()) {


            logger.info(
                    "Fetching product details productId={}",
                    itemRequest.getProductId()
            );


            Map<String,Object> product =
                    productClient.getProductById(
                            itemRequest.getProductId()
                    );


            if(product == null){

                throw new ProductNotFoundException(
                        "Product not found"
                );
            }



            Integer stock =
                    (Integer) product.get("stock");


            BigDecimal price =
                    new BigDecimal(
                            product.get("price").toString()
                    );



            if(stock < itemRequest.getQuantity()){


                throw new InsufficientStockException(
                        "Insufficient stock"
                );
            }



            // reduce stock

            productClient.reduceStock(
                    itemRequest.getProductId(),
                    itemRequest.getQuantity()
            );



            // create order item

            OrderItem orderItem = new OrderItem();


            orderItem.setProductId(
                    itemRequest.getProductId()
            );


            orderItem.setQuantity(
                    itemRequest.getQuantity()
            );


            orderItem.setPrice(price);



            orderItem.setOrder(order);



            orderItems.add(orderItem);



            BigDecimal itemTotal =
                    price.multiply(
                            BigDecimal.valueOf(
                                    itemRequest.getQuantity()
                            )
                    );


            totalAmount =
                    totalAmount.add(itemTotal);


        }



        order.setItems(orderItems);


        order.setTotalPrice(totalAmount);



        Order saved =
                orderRepository.save(order);



        logger.info(
                "Order created successfully id={}",
                saved.getId()
        );



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


        response.setUserEmail(order.getUserEmail());


        response.setTotalPrice(order.getTotalPrice());



        List<OrderItemResponse> items =
                order.getItems()
                        .stream()
                        .map(item -> {

                            OrderItemResponse itemResponse =
                                    new OrderItemResponse();


                            itemResponse.setProductId(
                                    item.getProductId()
                            );


                            itemResponse.setQuantity(
                                    item.getQuantity()
                            );


                            itemResponse.setPrice(
                                    item.getPrice()
                            );


                            return itemResponse;


                        })
                        .toList();



        response.setItems(items);


        return response;

    }


}

